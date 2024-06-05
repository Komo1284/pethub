package itbank.pethub.config;

import itbank.pethub.jwt.JWTFilter;
import itbank.pethub.jwt.JWTUtil;
import itbank.pethub.jwt.LoginFilter;
import itbank.pethub.oauth2.CustomClientRegistrationRepo;
import itbank.pethub.oauth2.CustomOAuth2AuthorizedClientService;
import itbank.pethub.oauth2.CustomSuccessHandler;
import itbank.pethub.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    private final AuthenticationConfiguration authenticationConfiguration;
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
            JWTUtil jwtUtil,
            AuthenticationConfiguration authenticationConfiguration) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customClientRegistrationRepo = customClientRegistrationRepo;
        this.customOAuth2AuthorizedClientService =  customOAuth2AuthorizedClientService;
        this.customSuccessHandler = customSuccessHandler;
        this.jdbcTemplate = jdbcTemplate;
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8081"));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);
                    configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));
                    return configuration;
                }))
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // Form 로그인 비활성화
                .formLogin(form -> form.disable())
                // HTTP Basic 인증 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())
                // 세션 관리
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 권한 요구 사항
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/member/login", "/", "/oauth2/**", "/member/signUp", "/member/join").permitAll()
                        .anyRequest().authenticated())
                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler))
                // 사용자 정의 필터 추가
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//
//                        CorsConfiguration configuration = new CorsConfiguration();
//
//                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8081"));
//                        configuration.setAllowedMethods(Collections.singletonList("*"));
//                        configuration.setAllowCredentials(true);
//                        configuration.setAllowedHeaders(Collections.singletonList("*"));
//                        configuration.setMaxAge(3600L);
//
//                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
//                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
//
//                        return configuration;
//                    }
//                }));
//
//
//        //csrf disable
//        http
//                .csrf((auth) -> auth.disable());
//
//        // form 로그인 방식 disable
//        http
//                .formLogin((auth) -> auth.disable());
//
//        // http basic 인증 방식 disable
//        http
//                .httpBasic((auth) -> auth.disable());
//
//
//
//        //JWTFilter 추가
//        http
//                .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);
//
//        // oauth2 로그인 방식
//        http
//                .oauth2Login((oauth2) -> oauth2
//                        .loginPage("/login").permitAll()
//                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
//                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
//                        .userInfoEndpoint((userInfoEndpointConfig ->
//                                userInfoEndpointConfig.userService(customOAuth2UserService)))
//                        .successHandler(customSuccessHandler)
//                );
//
//        // 글로벌 인가 작업
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/member/login", "/", "/oauth2/**", "/login",
//                                "/member/login/**", "/member/signUp"
//                                    , "/member/join").permitAll()
//
//                        // /admin은 ADMIN 역할을 가진자만 사용 가능
//                        // .requestMatchers("/admin").hasRole("ADMIN")
//
//                        // 로그인한사람만 접근가능
//                        .anyRequest().authenticated());
//
//        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
//        http
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
//
//        // 세션 설정
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
//
//
//        return http.build();
//    }

}
