package itbank.pethub.config.oauth;

import itbank.pethub.aop.PasswordEncoder;
import itbank.pethub.config.auth.PrincipalDetails;
import itbank.pethub.config.oauth.provider.GoogleUserInfo;
import itbank.pethub.config.oauth.provider.NaverUserInfo;
import itbank.pethub.config.oauth.provider.OAuth2UserInfo;
import itbank.pethub.model.UserDAO;
import itbank.pethub.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);


        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);
        System.out.println("getClientRegistration : " + oAuth2UserRequest.getClientRegistration());
        System.out.println("getAccessToken : " + oAuth2UserRequest.getAccessToken());
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        return processOAuth2User(oAuth2UserRequest, oAuth2User, session);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User,  HttpSession session) {

        OAuth2UserInfo oAuth2UserInfo = null;


        // 회원가입을 강제로 진행해볼 예정
        if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("Naver 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));

        } else {
            System.out.println("우리는 구글과 네이버만 지원해요");
        }


        Optional<UserVO> userOptional =
                userDAO.findByProviderAndProviderId(oAuth2UserInfo);

        UserVO user = new UserVO();
        if (userOptional.isPresent()) {
            user.setToken(oAuth2UserRequest.getAccessToken().toString());
            userDAO.updateUser(user);
            session.setAttribute("user", userDAO.findAll());

        } else {
            user.setUserid(oAuth2UserInfo.getProviderId());
            user.setName(oAuth2UserInfo.getName());
            user.setEmail(oAuth2UserInfo.getEmail());
            user.setNick(oAuth2UserInfo.getNickname());
            user.setPhone(oAuth2UserInfo.getPhone());
            user.setProvider(oAuth2UserInfo.getProvider());
            user.setProviderId(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId());
            userDAO.insertUser(user);


            session.setAttribute("user", userDAO.findAll());
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

}

