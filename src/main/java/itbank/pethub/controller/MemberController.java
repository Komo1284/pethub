package itbank.pethub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/myPage")
    public void myPage() {}

    @GetMapping("/findAcc")
    public void findAcc() {}

    @GetMapping("/login")
    public void login() {}

    @GetMapping("/memberUpdate")
    public void memberUpdate() {}

    @GetMapping("/signUp")
    public void signUp() {}
}
