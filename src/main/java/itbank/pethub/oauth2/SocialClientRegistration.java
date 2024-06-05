package itbank.pethub.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

@Component
public class SocialClientRegistration {

    // Naver 관련 api
    public ClientRegistration naverclientRegistration() {

        return ClientRegistration.withRegistrationId("naver")
                .clientId("QpBnbk7arcmKPKD1wYrM")
                .clientSecret("AXFiNzxWTx")
                .redirectUri("http://localhost:8081/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email", "nickname")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

//    // google 관련 api
//    public ClientRegistration googleclientRegistration() {
//
//        return ClientRegistration.withRegistrationId("google")
//                .clientId("287735118719-7amr2loj78mtgnglha6rhudq7ouao3j5.apps.googleusercontent.com")
//                .clientSecret("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")
//                .redirectUri("http://localhost:8081/login/oauth2/code/google")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("name", "email", "nickname")
//                .build();
//    }

}
