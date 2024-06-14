package itbank.pethub.config.auth;

// 시큐리티 설정에서 loginProcessingUrl("/member/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 실행

import itbank.pethub.model.UserDAO;
import itbank.pethub.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired

    private UserDAO userDAO;

    // 시큐리티 session(Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        UserVO userVO = userDAO.findByUserid(userid);
        if (userVO != null) {
            return new PrincipalDetails(userVO);
        }
        else {

            throw new UsernameNotFoundException("User not found with username: " + userid);
        }

    }
}
