package itbank.pethub.jwt;

import itbank.pethub.dto.CustomUserDetails;
import itbank.pethub.dto.MemberDetails;
import itbank.pethub.model.MemberDAO;
import itbank.pethub.vo.MemberVO;
import itbank.pethub.vo.RefreshVO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MemberDAO dao;

//    @PostConstruct
//    public void init() {
//        super.setFilterProcessesUrl("http://localhost:8081/member/login");
//    }

    @Bean
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String userid = obtainUsername(request);
        String userpw = obtainPassword(request);


        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userid, userpw, null);

        return authenticationManager.authenticate(authToken);
    }

    @Bean
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws ServletException, IOException {

        System.out.println("success");

        //유저 정보
        String username = authentication.getName();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        MemberVO memberVO = new MemberVO();

        memberVO.setUserid(memberDetails.getUsername());
        memberVO.setUserpw(memberDetails.getPassword());

        // 가입 여부 확인
        if (dao.selectOne(memberVO) != null) {
            MemberVO user = dao.selectOne(memberVO);
            user.setRole(0);

            // 가입 되어있지만 Access 와 refresh 여부에 따른 생성

            // 두 토큰 다 만료되었을 때
            if (user.getAccessToken() == null && dao.selectRefresh(user.getUserid()) == null) {

                // Access & Refresh 토큰 생성
                String access = jwtUtil.createJwt("access", user.getUserid(), 0, 600000L);
                String refresh = jwtUtil.createJwt("refresh", user.getUserid(), 0, 86400000L);


                // Access & Refresh 토큰 DB 저장
                user.setAccessToken(access);
                addAccesstoken(user);
                addRefreshEntity(user.getUserid(), refresh, 86400000L);

                //응답 설정
                response.addHeader("Authorization", "Bearer " + access);
                response.addCookie(createCookie("refresh", refresh));
                response.setStatus(HttpStatus.OK.value());
                response.sendRedirect("http://localhost:8081/reissue");

            }
            // Access 토큰만 만료되었을 때
            else if (user.getAccessToken() == null && dao.selectRefresh(user.getUserid()) != null ) {
                //토큰 생성
                String access = jwtUtil.createJwt("access", user.getUserid(), user.getRole(), 600000L);

                // Access 토큰 DB 저장
                user.setAccessToken(access);
                addAccesstoken(user);

                // 만료되지않은 refresh 토큰 불러오기
                String refresh = dao.selectRefresh(user.getUserid()).getRefresh();

                //응답 설정
                response.addHeader("Authorization", "Bearer " + access);
                response.addCookie(createCookie("refresh", refresh));
                response.setStatus(HttpStatus.OK.value());
                response.sendRedirect("http://localhost:8081/reissue");
            }
        }




    }

    @Bean
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String userid, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setUserid(userid);
        refreshVO.setRefresh(refresh);
        refreshVO.setExpiration(date.toString());

        dao.insertRefresh(refreshVO);
    }

    private void addAccesstoken(MemberVO user) {

        dao.addAccesstoken(user);

    }
}
