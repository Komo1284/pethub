package itbank.pethub.controller;

import itbank.pethub.service.CartService;
import itbank.pethub.service.OrderService;
import itbank.pethub.vo.CartVO;
import itbank.pethub.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cs;

    @Autowired
    private OrderService os;
    // 장바구니 항목 조회
    @GetMapping("/cart")
    public List<CartVO> getCartItems(@RequestParam Long userId) {
        return CartService.getCartItemsByUser(userId);
    }

    // 장바구니에 항목 추가
    @PostMapping("/cart")
    public void addToCart(@RequestBody CartVO cartItem) {
        CartService.addToCart(cartItem);
    }

    // 장바구니에서 항목 제거
    @DeleteMapping("/cart/{id}")
    public void removeFromCart(@PathVariable Long id) {
        CartService.removeFromCart(id);
    }

    // 결제 생성
    @PostMapping("/pay")
    public OrderVO createOrder(@RequestParam Long userId, @RequestBody List<CartItemVO> cartItems, @RequestParam(required = false) String couponCode) {
        return orderService.createOrder(userId, cartItems, couponCode);
    }

}
