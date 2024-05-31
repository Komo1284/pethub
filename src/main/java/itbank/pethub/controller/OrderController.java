package itbank.pethub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mall/order")
public class OrderController {

    @GetMapping("/cart")
    public void cart() {}

    @GetMapping("/orderStatus")
    public void orderStatus() {}
}
