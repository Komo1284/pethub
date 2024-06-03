package itbank.pethub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/product_registration")
    public void adminProductRegistration() {}

    @GetMapping("/admin/manage_orders")
    public void adminManageOrders() {}
}
