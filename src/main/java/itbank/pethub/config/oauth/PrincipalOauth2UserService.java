package itbank.pethub.config.oauth;

import itbank.pethub.aop.PasswordEncoder;
import itbank.pethub.config.auth.PrincipalDetails;
import itbank.pethub.config.oauth.provider.GoogleUserInfo;
import itbank.pethub.config.oauth.provider.NaverUserInfo;
import itbank.pethub.config.oauth.provider.OAuth2UserInfo;
import itbank.pethub.model.MemberDAO;

import itbank.pethub.vo.MemberVO;
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
    private MemberDAO memberDAO;

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


        MemberVO memberVO = new MemberVO();

        if (oAuth2UserInfo != null) {
            memberVO.setUserid(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId());
            memberVO.setName(oAuth2UserInfo.getName());
            memberVO.setNick(oAuth2UserInfo.getNickname());
            memberVO.setRole(0);
            memberVO.setProvider(oAuth2UserInfo.getProvider());
            memberVO.setEmail(oAuth2UserInfo.getEmail());
            memberVO.setPhone(oAuth2UserInfo.getPhone());
            if (memberDAO.countByUserId(memberVO.getUserid()) > 0) {
                System.out.println("이미 존재하는 ID 입니다. 자동으로 업데이트 진행! 바로 로그인");
                memberDAO.update(memberVO);
                session.setAttribute("user", memberDAO.selectSnsOne(memberVO));
            }
            else {
                
                System.out.println("없는 새로운 SNS 계정. DB에 추가 후 로그인");
                memberDAO.insert(memberVO);
                session.setAttribute("user", memberDAO.selectSnsOne(memberVO));
            }




        }

        return new PrincipalDetails(memberVO, oAuth2User.getAttributes());
    }

}

