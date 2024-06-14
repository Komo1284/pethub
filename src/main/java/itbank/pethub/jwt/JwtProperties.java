package itbank.pethub.jwt;

import org.springframework.beans.factory.annotation.Value;


public interface JwtProperties {

    String JWT_COOKIE_NAME = "JWT-TOKEN";
    String SECRET = "vmfhaltmskdlstkfkdgodyrogkfwkdbalrogkfmkdbalaaaaaaaaaaaaaaaabbbbb"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 3600; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";


}
