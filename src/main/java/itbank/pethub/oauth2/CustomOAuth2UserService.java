package itbank.pethub.oauth2;


import itbank.pethub.config.auth.PrincipalDetails;
import itbank.pethub.exception.OAuth2AuthenticationProcessingException;
import itbank.pethub.model.MemberDAO;
import itbank.pethub.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberDAO memberDAO;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            // token을 통해 응답받은 회원정보
            System.out.println("oAuth2User : " + oAuth2User);
            System.out.println("getClientRegistration : " + oAuth2UserRequest.getClientRegistration());
            System.out.println("getAccessToken : " + oAuth2UserRequest.getAccessToken());
            System.out.println("getAttributes : " + oAuth2User.getAttributes());

            String provider = oAuth2UserRequest.getClientRegistration().getClientId(); // google, naver 등등..
            String providerId = oAuth2User.getAttribute("sub");
            String userid = provider + "_" + providerId; // google_sdgagagd
            String userpw = passwordEncoder.encode("겟인데어");
            String email = oAuth2User.getAttribute("email");
            int role = 0;

            MemberVO memberVO = memberDAO.findByUserid(userid);

            if (memberVO == null) {
                memberVO = MemberVO.builder()
                        .userid(userid)
                        .userpw(userpw)
                        .email(email)
                        .role(role)
                        .provider(provider)
                        .providerId(providerId)
                        .build();

                memberDAO.insert(memberVO);

                return new PrincipalDetails(memberVO, oAuth2User.getAttributes());
            }else  {

            }


            return processOAuth2User(oAuth2UserRequest, oAuth2User);

        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
                accessToken,
                oAuth2User.getAttributes());

        // OAuth2UserInfo field value validation
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}
