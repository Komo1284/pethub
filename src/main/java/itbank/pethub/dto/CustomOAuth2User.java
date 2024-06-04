package itbank.pethub.dto;

import itbank.pethub.entity.MemberEntity;
import itbank.pethub.vo.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private MemberVO member;

    public CustomOAuth2User(MemberVO member) {
        this.member = member;
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
                return member.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return member.getName();
    }

    public String getUserid() {
        return member.getUserid();
    }


}
