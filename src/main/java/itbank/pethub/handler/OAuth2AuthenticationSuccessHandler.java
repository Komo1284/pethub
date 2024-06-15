package itbank.pethub.handler;

import itbank.pethub.oauth2.util.CookieUtils;
import itbank.pethub.oauth2.OAuth2Provider;
import itbank.pethub.oauth2.service.OAuth2UserPrincipal;
import itbank.pethub.oauth2.OAuth2UserUnlinkManager;
import itbank.pethub.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import itbank.pethub.service.MemberService;
import itbank.pethub.vo.MemberVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static itbank.pethub.repository.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static itbank.pethub.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private MemberService ms;

    @Autowired
    private HttpSession session;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl;

        targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        System.out.println(targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        if ("login".equalsIgnoreCase(mode)) {
            // TODO: DB 저장
            // TODO: 액세스 토큰, 리프레시 토큰 발급
            // TODO: 리프레시 토큰 DB 저장
            log.info("email={}, name={}, nickname={}, accessToken={}, id={} ",
                    principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNick(),
                    principal.getUserInfo().getAccessToken(),
                    principal.getUserInfo().getId(),
                    principal.getUserInfo().getPhone(),
                    principal.getUserInfo().getProvider()

            );



            String userid = principal.getUserInfo().getProvider() + "_" + principal.getUserInfo().getId();
            String userpw = principal.getUserInfo().getId() + "_pethub";

            System.out.println(userid);
            System.out.println(principal.getAttributes().get("nickname").toString());

            Boolean isExist = ms.isUserIdExists(userid);

            MemberVO member = new MemberVO();

            member.setUserid(userid);
            member.setUserpw(userpw);
            member.setNick(principal.getAttributes().get("nickname").toString());
            member.setPhone(principal.getUserInfo().getPhone());
            member.setRole(0);
            member.setEmail(principal.getUserInfo().getEmail());
            member.setProvider(principal.getUserInfo().getProvider().toString());
            member.setName(principal.getUserInfo().getName());



            if (!isExist) {

                int row = ms.addSnsuser(member);

                if (row != 0) {

                    MemberVO memberVO = ms.SnsNoAddress(member);
                    memberVO.setAddress_details("상세주소를 꼭 마이페이지 가서 입력!");
                    memberVO.setPrimary_address("마이페이지가서 주소를 꼭 입력해주세요!");
                    memberVO.setZip_code(0);

                    ms.insertSnsAdd(memberVO);


                    System.out.println("DB 저장 성공!");
                }

            }
            else {
                ms.updateSnsUser(member);
            }

            session.setAttribute("user", ms.Snslogin(member));

            String accessToken = "test_access_token";
            String refreshToken = "test_refresh_token";

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("access_token", accessToken)
                    .queryParam("refresh_token", refreshToken)
                    .build().toUriString();

        } else if ("unlink".equalsIgnoreCase(mode)) {

            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            MemberVO member2 = new MemberVO();

            String userid = principal.getUserInfo().getProvider() + "_" + principal.getUserInfo().getId();
            String userpw = principal.getUserInfo().getId() + "_pethub";

            member2.setUserid(userid);
            member2.setUserpw(userpw);
            member2.setNick(principal.getAttributes().get("nickname").toString());
            member2.setPhone(principal.getUserInfo().getPhone());
            member2.setRole(0);
            member2.setEmail(principal.getUserInfo().getEmail());
            member2.setProvider(principal.getUserInfo().getProvider().toString());
            member2.setName(principal.getUserInfo().getName());


            ms.updateSnsUser(member2);
            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            session.removeAttribute("user");

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    // User 정보들
    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
