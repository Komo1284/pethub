package itbank.pethub.service;

import itbank.pethub.dto.CustomOAuth2User;
import itbank.pethub.dto.GoogleResponse;
import itbank.pethub.dto.NaverResponse;
import itbank.pethub.dto.OAuth2Response;

import itbank.pethub.repository.MemberRepository;
import itbank.pethub.vo.MemberVO;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
       this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser (OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        // DB저장 및 업데이트
        String userid = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        MemberVO existData = memberRepository.findByUserid(userid);
        String role = null;

        if (existData == null) {
            MemberVO member = new MemberVO();

            member.setUserid(userid);
            member.setName(oAuth2Response.getName());
            member.setEmail(oAuth2Response.getEmail());
            member.setUserid(userid);
            member.setRole("ROLE_USER");

            memberRepository.save(member);

            return new CustomOAuth2User(member);

        }
        else {
            existData.setUserid(userid);
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            memberRepository.save(existData);

            MemberVO member = new MemberVO();

            member.setName(oAuth2Response.getName());
            member.setEmail(oAuth2Response.getEmail());
            member.setUserid(userid);
            member.setRole("ROLE_USER");



            return new CustomOAuth2User(member);
        }


    }

}
