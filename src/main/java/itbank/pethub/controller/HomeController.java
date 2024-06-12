package itbank.pethub.controller;

import itbank.pethub.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private MemberService ms;

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/login/oauth2/code/naver")
    public String snsloginnaver() {


        return "redirect:/";
    }

    @GetMapping("/login/oauth2/code/google")
    public String snslogingoogle() {
        return "redirect:/";
    }


    @GetMapping("/login/oauth2/code/kakao")
    public String snsloginkakao() {
        return "redirect:/";
    }

    @GetMapping("http://localhost:8081/oauth2/authorization/naver?redirect_uri=http://localhost:8081&mode=unlink")
    public String snslogoutnaver() {
        return "redirect:/";
    }

}
