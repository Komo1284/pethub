package itbank.pethub.config;

import itbank.pethub.config.oauth.PrincipalOauth2UserService;
import itbank.pethub.model.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    private final MemberDAO dao;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin
                        .loginPage("/member/login")
                        .usernameParameter("userid")
                        .passwordParameter("userpw")
                        .loginProcessingUrl("/member/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/reissue").permitAll()
                        .requestMatchers("/","/member/login", "/member/signUp", "member/logout"
                                        , "member/myPage", "member/memberUpdate").permitAll()
                        .requestMatchers(antMatcher("/api/user/**")).hasRole("ROLE_USER")
                        .requestMatchers(antMatcher("/manager/**")).hasRole("ROLE_MANAGER")
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(configure ->
                        configure
                                .loginPage("/member/login")
                                // sns 로그인이 완료된 후의 후처리가 필요함
                                // 1. 코드 받기
                                // 2. 엑세스토큰(권한)
                                // 3. 사용자 프로필 정보를 가져오고
                                // 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
                                // 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소 필요)

                                .authorizationEndpoint(config -> config
                                        .userInfoEndpoint(endpoint -> endpoint
                                            .userService(principalOauth2UserService))
                ));

        return http.build();
    }
}


