package itbank.pethub.oauth;

import itbank.pethub.vo.RoleVO;
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
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers ->
                        headers.frameOptions(frameOptionsConfig
                                -> frameOptionsConfig.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/member/login"
                        ).permitAll()
                        .requestMatchers("/mytravel/**").hasRole(RoleVO.GUEST.name())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/")
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig
                                -> userInfoEndpointConfig.userService(customOAuth2UserService))
                );
        return http.build();
    }
}