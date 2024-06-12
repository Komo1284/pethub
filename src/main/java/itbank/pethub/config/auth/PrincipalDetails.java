package itbank.pethub.config.auth;


// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줌 (Security ContextHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 Member 정보가 있어야 됨
// User 오브젝트 타입 => MemberDetails 타입 객체

import itbank.pethub.vo.MemberVO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {




    private MemberVO memberVO;
    private Map<String, Object> attributes;

    public PrincipalDetails(MemberVO memberVO) {
        this.memberVO = memberVO;
    }

    public PrincipalDetails(MemberVO memberVO, Map<String, Object> attributes) {
        this.memberVO = memberVO;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                String realRole = "";
                if (memberVO.getRole() == 0)  {
                    realRole = "ROLE_USER";
                }
                else if (memberVO.getRole() == 1)  {
                    realRole = "ROLE_ADMIN";
                }
                else if (memberVO.getRole() == 2)  {
                    realRole = "ROLE_MANAGER";
                }

                return realRole;
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return memberVO.getUserpw();
    }

    @Override
    public String getUsername() {
        return memberVO.getUserid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 1년동안 회원이 로그인 안하면 휴면계정으로 변경
        // memberVO.getLoginDate()
        // 현재시간 - 로그인 시간 = 1년 이상

        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
