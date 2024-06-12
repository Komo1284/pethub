package itbank.pethub.dto;

import itbank.pethub.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class MemberDetails implements UserDetails {

    private final MemberVO memberVO;



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
        return true;
    }
}
