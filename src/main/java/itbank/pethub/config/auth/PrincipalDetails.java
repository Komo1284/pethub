package itbank.pethub.config.auth;

import itbank.pethub.vo.UserVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private static final long serialVersionUID = 1L;
    private UserVO userVO;
    private Map<String, Object> attributes;

    // 일반 시큐리티 로그인 시 사용
    public PrincipalDetails(UserVO userVO) {
        this.userVO = userVO;
    }

    // OAuth 2.0 로그인 시 사용
    public PrincipalDetails(UserVO userVO, Map<String, Object> attributes) {
        this.userVO = userVO;
        this.attributes = attributes;
    }

    public UserVO getUser() {
        if (userVO == null) {
            return new UserVO(); // 빈 UserVO 객체를 반환하거나 null 처리에 대한 방어 로직 추가
        }
        return userVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userVO.getUserpw();
    }

    @Override
    public String getUsername() {
        return userVO.getUserid();
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
