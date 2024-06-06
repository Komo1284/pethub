package itbank.pethub.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 인가(접근권한) 설정
        http.().antMatchers("/").permitAll();
        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeHttpRequests().antMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER");
        http.authorizeHttpRequests().antMatchers("/user2/loginSuccess").hasAnyRole("3", "4", "5");

        // 사이트 위변조 요청 방지
        http
                .csrf().disable();

        // 로그인 설정
        http.formLogin()
                .loginPage("/user2/login")
                .defaultSuccessUrl("/user2/loginSuccess")
                .failureUrl("/user2/login?success=100)")
                .usernameParameter("uid")
                .passwordParameter("pass");

        // 로그아웃 설정
        http.logout()
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
                .logoutSuccessUrl("/user2/login?success=200");

        // 사용자 인증 처리 컴포넌트 서비스 등록
        http.userDetailsService(service);

        return http.build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //csrf 차단 해제
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/",
                        "/home/**",
                        "/test/**"
                                .permitAll()
                                .antMatchers("/mytravel/**").hasRole(Role.GUEST.name())
                                .anyRequest().authenticated()
                                .and()
                                .logout().logoutSuccessUrl("/")
                                .and()
                                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }

}