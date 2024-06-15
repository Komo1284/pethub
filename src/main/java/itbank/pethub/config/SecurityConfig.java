package itbank.pethub.config;


import itbank.pethub.service.OAuth2MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuth2MemberService oAuth2MemberService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable());

        http
                .formLogin((login) -> login.disable());

        http
                .httpBasic((basic) -> basic.disable());

        http
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(config -> config
                                .userService(oAuth2MemberService)));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login/oauth2/code/google", "/login/oauth2/code/google",
                                "/login/oauth2/code/kakao",
                                "/member/loginPage","/member/logout", "/member/myPage", "/member/signUp").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}

