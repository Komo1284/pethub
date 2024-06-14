package itbank.pethub.controller;

import java.util.List;

import itbank.pethub.config.auth.PrincipalDetails;
import itbank.pethub.model.UserDAO;
import itbank.pethub.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
// @CrossOrigin  // CORS 허용
public class RestApiController {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RestApiController(BCryptPasswordEncoder bCryptPasswordEncoder, UserDAO userDAO) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDAO = userDAO;
    }

    // 모든 사람이 접근 가능
    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.

    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getId());
        System.out.println("principal : "+principal.getUser().getUserid());
        System.out.println("principal : "+principal.getUser().getUserpw());

        return "<h1>user</h1>";
    }

    // 매니저 혹은 어드민이 접근 가능
    @GetMapping("manager/reports")
    public String reports() {
        return "<h1>reports</h1>";
    }

    // 어드민이 접근 가능
    @GetMapping("admin/users")
    public List<UserVO> users(){
        return userDAO.findAll();
    }

    @PostMapping("join")
    public String join(@RequestBody UserVO user) {
        user.setUserpw(bCryptPasswordEncoder.encode(user.getUserpw()));
        user.setRole("USER");
        userDAO.joinUser(user);
        return "회원가입완료";
    }

}