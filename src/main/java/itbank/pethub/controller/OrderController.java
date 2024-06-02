package itbank.pethub.controller;

import itbank.pethub.service.OrderService;
import itbank.pethub.vo.CartItemVO;
import itbank.pethub.vo.CartVO;
import itbank.pethub.vo.MemberVO;
import itbank.pethub.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/order")
public class OrderController {

        @Autowired
        private OrderService os;

        @GetMapping("/cart")
        public String cart(@RequestParam("memberId") int memberId, Model model) {
            CartVO cart = os.getCartByMemberId(memberId);
            model.addAttribute("cart", cart);
            return "cart";
        }

        @PostMapping("/cart/add")
        public String addToCart(@ModelAttribute CartItemVO cartItem) {
            os.addItemToCart(cartItem);
            return "redirect:/order/cart?memberId=" + cartItem.getCartId();
        }

        @PostMapping("/cart/remove")
        public String removeFromCart(@RequestParam("cartItemId") int cartItemId) {
            os.removeItemFromCart(cartItemId);
            return "장바구니에서 제거되었습니다";
        }

        @PostMapping("/cart/update")
        public String updateItemQuantity(@RequestParam("cartItemId") int cartItemId, @RequestParam("quantity") int quantity) {
            os.updateItemQuantity(cartItemId, quantity);
            return "redirect:/order/cart?memberId=" + cartItemId;
        }

        @GetMapping("/pay")
        public String pay(@RequestParam("memberId") int memberId, Model model) {
            CartVO cart = os.getCartByMemberId(memberId);
            MemberVO member = os.getMemberById(memberId);
            model.addAttribute("cart", cart);
            model.addAttribute("member", member);
            return "pay";
        }

        @PostMapping("/checkout")
        public String checkout(@RequestParam("memberId") int memberId) {
            OrderVO order = os.checkout(memberId);
            try {
                os.processPayment(order);
            } catch (Exception e) {
                e.printStackTrace();
                return "결제 실패";
            }
            return "redirect:/order/success";
        }

        @GetMapping("/success")
        public String success() {
            // 결제 후 주문 현황(확인) 페이지
            return "주문 완료";
        }
    }





