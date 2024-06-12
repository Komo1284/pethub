package itbank.pethub.dto;

import itbank.pethub.vo.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private MemberVO memberVO;

    public CustomOAuth2User(MemberVO memberVO) {
        this.memberVO = memberVO;
    }

    // 모든 데이터를 다 받아옴
    // 하지만, 구글이나 네이버 등 각 SNS 마다 키값이 달라서 일단 구현 X
    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    // Role 값을 의미
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
    public String getName() {
        return memberVO.getName();
    }

    public String getUserid() {
        return memberVO.getUserid();
    }


}
