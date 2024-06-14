package itbank.pethub.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import itbank.pethub.config.auth.PrincipalDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider  {
    private String secretKey;
    public static final long EXPIRATION_TIME = 864000000;
    public static final String JWT_COOKIE_NAME = "JWT-TOKEN";

    public JwtProvider() {
        // 초기화 로직
    }

    public JwtProvider(@Value("${spring.jwt.secret}") String secret) {
       this.secretKey = secret;

    }


    public void createToken(HttpServletResponse response, PrincipalDetails principalDetails) {

        Algorithm algorithm = Algorithm.HMAC512(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("userid", principalDetails.getUser().getUserid())
                .sign(algorithm);

        Cookie cookie = new Cookie(JWT_COOKIE_NAME, jwtToken);
        cookie.setMaxAge((int) EXPIRATION_TIME / 1000); // seconds
        cookie.setPath("/"); // cookie path 설정 (루트 경로)
        cookie.setHttpOnly(true); // JavaScript로 접근 불가
        response.addCookie(cookie);
    }

}
