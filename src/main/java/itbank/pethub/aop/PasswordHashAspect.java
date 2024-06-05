package itbank.pethub.aop;

import itbank.pethub.vo.MemberVO;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PasswordHashAspect {

    @Before("execution(* itbank.pethub.service.MemberService.login(..)) && args(input) || " +
            "execution(* itbank.pethub.service.MemberService.update(..)) && args(input) || " +
            "execution(* itbank.pethub.service.MemberService.signUp(..)) && args(input) || " +
            "execution(* itbank.pethub.service.MemberService.updateNoProfile(..)) && args(input)")
    public void hashPasswordBeforeAnyMethod(MemberVO input) {
        input.setUserpw(PasswordEncoder.encode(input.getUserpw()));
    }
}
