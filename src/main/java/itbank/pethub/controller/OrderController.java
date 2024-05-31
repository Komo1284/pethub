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

    // 장바구니 항목 조회
    @GetMapping("/cart")
    public List<CartVO> getCartItems(@RequestParam int member_id) {
        return cs.getCartItems(member_id);
    }

}
