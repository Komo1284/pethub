package itbank.pethub.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import itbank.pethub.config.auth.PrincipalDetails;
import itbank.pethub.jwt.JwtProperties;
import itbank.pethub.model.UserDAO;
import itbank.pethub.vo.UserVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDAO userDAO;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDAO userDAO) {
        super(authenticationManager);
        this.userDAO = userDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 쿠키에서 JWT 토큰 추출
        String jwtToken = extractTokenFromCookie(request, JwtProperties.JWT_COOKIE_NAME);

        // JWT 토큰이 없으면 다음 필터로 이동
        if (jwtToken == null) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증 및 인증 객체 생성
        String username = getUsernameFromToken(jwtToken);
        if (username != null) {
            UserVO user = userDAO.findByUserid(username);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails,
                            null,
                            principalDetails.getAuthorities());

            // SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getUsernameFromToken(String jwtToken) {
        try {
            return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(jwtToken.replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
