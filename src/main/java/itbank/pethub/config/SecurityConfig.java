package itbank.pethub.config;

import itbank.pethub.jwt.JWTFilter;
import itbank.pethub.jwt.JWTUtil;
import itbank.pethub.oauth2.CustomClientRegistrationRepo;
import itbank.pethub.oauth2.CustomOAuth2AuthorizedClientService;
import itbank.pethub.oauth2.CustomSuccessHandler;
import itbank.pethub.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;
    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JdbcTemplate jdbcTemplate;
    private final JWTUtil jwtUtil;


    public SecurityConfig(
            CustomOAuth2UserService customOAuth2UserService,
            CustomClientRegistrationRepo customClientRegistrationRepo,
            CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService,
            CustomSuccessHandler customSuccessHandler,
            JdbcTemplate jdbcTemplate,
            JWTUtil jwtUtil) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customClientRegistrationRepo = customClientRegistrationRepo;
        this.customOAuth2AuthorizedClientService =  customOAuth2AuthorizedClientService;
        this.customSuccessHandler = customSuccessHandler;
        this.jdbcTemplate = jdbcTemplate;
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        // form 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        // http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());


        //JWTFilter 추가
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // oauth2 로그인 방식
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                        .userInfoEndpoint((userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)))
                        .successHandler(customSuccessHandler)
                );

        // 글로벌 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/member/login", "/", "/oauth2/**", "/member/login/**").permitAll()
                        // 로그인한사람만 접근가능
                        .anyRequest().authenticated());


        // 세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
