package itbank.pethub.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getPhone() {
        return (String) attributes.get("mobile").toString();
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("nickname").toString();
    }
}
