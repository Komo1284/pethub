package itbank.pethub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mall/shop")
public class ShopController {

    @GetMapping("/main")
    public void main() {}

    @GetMapping("/category_view")
    public void categoryView() {}
}
