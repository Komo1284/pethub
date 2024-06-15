package itbank.pethub.service;

import itbank.pethub.oauth.GoogleUserInfo;
import itbank.pethub.oauth.NaverUserInfo;
import itbank.pethub.oauth.OAuth2UserInfo;
import itbank.pethub.model.MemberDAO;
import itbank.pethub.oauth.MemberOAuth2User;
import itbank.pethub.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2MemberService extends DefaultOAuth2UserService {

    @Autowired
    private HttpSession session;

    @Autowired
    private MemberDAO memberDAO;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;
        if (registrationId.equals("naver")) {

            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else {

            return null;
        }

        String userid  = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();

        MemberVO isExistmember = memberDAO.findByUserid(userid);

        int DB_role = 0;

        if (isExistmember == null) {
            MemberVO member = new MemberVO();
            member.setUserid(userid);
            member.setEmail(oAuth2UserInfo.getEmail());
            member.setPhone(oAuth2UserInfo.getPhone());
            member.setProvider(oAuth2UserInfo.getProvider());
            member.setNick(oAuth2UserInfo.getNickname());
            member.setRole(DB_role);
            member.setPhone(oAuth2UserInfo.getPhone());
            member.setUserpw(oAuth2UserInfo.getProviderId());

            memberDAO.insert(member);

            session.setAttribute("user", memberDAO.selectSnsOne(member));

        }
        else {

            isExistmember.setUserid(userid);
            isExistmember.setEmail(oAuth2UserInfo.getEmail());
            isExistmember.setPhone(oAuth2UserInfo.getPhone());
            isExistmember.setProvider(oAuth2UserInfo.getProvider());
            isExistmember.setNick(oAuth2UserInfo.getNickname());
            isExistmember.setRole(DB_role);
            isExistmember.setPhone(oAuth2UserInfo.getPhone());
            isExistmember.setUserpw(oAuth2UserInfo.getProviderId());

            memberDAO.updateSns(isExistmember);

            session.setAttribute("user", memberDAO.selectSnsOne(isExistmember));

        }


        String role = "ROLE_USER";

        return new MemberOAuth2User(oAuth2UserInfo, role);

    }

}
