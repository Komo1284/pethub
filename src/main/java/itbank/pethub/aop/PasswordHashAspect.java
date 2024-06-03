package itbank.pethub.aop;

import itbank.pethub.vo.MemberVO;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PasswordHashAspect {

    @Before("execution(* itbank.pethub.service.MemberService.login(..)) && args(input)")
    public void hashPasswordBeforeLogin(MemberVO input) {
        input.setUserpw(PasswordEncoder.encode(input.getUserpw()));
    }

    @Before("execution(* itbank.pethub.service.MemberService.signUp(..)) && args(input)")
    public void hashPasswordBeforeSignUp(MemberVO input) {
        input.setUserpw(PasswordEncoder.encode(input.getUserpw()));
    }

    @Before("execution(* itbank.pethub.service.MemberService.update(..)) && args(input)")
    public void hashPasswordBeforeUpdate(MemberVO input) {
        input.setUserpw(PasswordEncoder.encode(input.getUserpw()));
    }

    @Before("execution(* itbank.pethub.service.MemberService.updateNoProfile(..)) && args(input)")
    public void hashPasswordBeforeUpdateNoProfile(MemberVO input) {
        input.setUserpw(PasswordEncoder.encode(input.getUserpw()));
    }
}
