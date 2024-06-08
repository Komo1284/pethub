package itbank.pethub.controller;

import itbank.pethub.service.MemberService;
import itbank.pethub.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService ms;

    @GetMapping("/myPage")
    public void myPage() {}

    @GetMapping("/findAcc")
    public void findAcc() {}

    @GetMapping("/login")
    public void login() {}

    @PostMapping("/login")
    public ModelAndView login(MemberVO input, HttpSession session) {

        ModelAndView mav = new ModelAndView();
        session.setAttribute("user", ms.login(input));
        mav.setViewName("redirect:/");

        return mav;

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/memberUpdate")
    public void memberUpdate() {}

    @GetMapping("/signUp")
    public void signUp() {}
}
