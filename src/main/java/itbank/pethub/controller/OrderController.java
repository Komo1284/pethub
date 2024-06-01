package itbank.pethub.controller;

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
    private OrderService os;

    @PostMapping("/cart")
    public void addCart(@RequestBody CartVO order_item) {
        os.addCart(order_item);
    }

    @PostMapping("/pay")
    public OrderVO createOrder(@RequestParam int member_id,@RequestBody OrderVO order_item, @RequestParam(required = false) String couponCode) {
        return os.createOrder(member_id, order_item, couponCode);
    }




}
