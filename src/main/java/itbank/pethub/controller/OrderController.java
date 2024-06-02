package itbank.pethub.controller;

import itbank.pethub.service.OrderService;
import itbank.pethub.vo.CartVO;
import itbank.pethub.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/order")
public class OrderController {

        @Autowired
        private OrderService os;

        @GetMapping("/cart")
        public String viewCart(Model model, @RequestParam("memberId") int memberId) {
            List<CartVO> cartItems = os.getCartItems(memberId);
            int totalPrice = os.calculateTotalPrice(cartItems);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("memberId", memberId);

            return "cart";
        }

        // 장바구니 항목 수정
        @PostMapping("/cart/update")
        public String updateCart(@RequestParam("memberId") int memberId, @RequestParam("itemId") int itemId, @RequestParam("count") int count) {
            os.updateCartItem(itemId, count);
            return "redirect:/order/cart?memberId=" + memberId;
        }

        // 장바구니 항목 삭제
        @PostMapping("/cart/delete")
        public String deleteCartItem(@RequestParam("memberId") int memberId, @RequestParam("itemId") int itemId) {
            os.deleteCartItem(itemId);
            return "redirect:/order/cart?memberId=" + memberId;
        }

        // 결제 페이지 보기
        @GetMapping("/pay")
        public String viewPaymentPage(Model model, @RequestParam("memberId") int memberId, @RequestParam("itemIds") List<Integer> itemIds) {
            List<CartVO> selectedItems = os.getSelectedItems(itemIds);
            MemberVO memberInfo = os.getMemberInfo(memberId);
            model.addAttribute("selectedItems", selectedItems);
            model.addAttribute("memberInfo", memberInfo);
            model.addAttribute("totalPrice", os.calculateTotalPrice(selectedItems));
            return "payment";
        }

        // 결제 처리
        @PostMapping("/pay/process")
        public String processPayment(@RequestParam("payMethod") String payMethod, @RequestParam("itemIds") List<Integer> itemIds, @RequestParam("memberId") int memberId, @RequestParam("merchantUid") String merchantUid) {
            os.processPayment(payMethod, itemIds, memberId, merchantUid);
            return "redirect:/order/success";
        }

        // 결제 성공 페이지
        @GetMapping("/success")
        public String paymentSuccess() {
            return "success";
        }
    }





