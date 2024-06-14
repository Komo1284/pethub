package itbank.pethub.config;



import itbank.pethub.config.auth.PrincipalDetailsService;
import itbank.pethub.config.oauth.PrincipalOauth2UserService;
import itbank.pethub.jwt.JwtAuthenticationFilter;
import itbank.pethub.jwt.JwtAuthorizationFilter;
import itbank.pethub.jwt.JwtProvider;
import itbank.pethub.model.UserDAO;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
public class SecurityConfig {

    @Autowired
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private UserDAO userDAO;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .addFilter(corsConfig.corsFilter())
                .formLogin(formLogin -> formLogin
                        .loginPage("/member/login")
                        .usernameParameter("userid")
                        .passwordParameter("userpw")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll())
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/","/member/login", "/member/signUp", "member/logout"
                                , "member/myPage", "member/memberUpdate").permitAll()
                        .requestMatchers(antMatcher("/api/v1/user/**")).hasRole("USER")
//                       .requestMatchers(antMatcher("/manager/**")).hasRole("MANAGER")
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .anyRequest().authenticated()
                )
//                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(configure ->
                        configure
                                .userInfoEndpoint(config -> config
                                        .userService(principalOauth2UserService))
                )
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration), userDAO), JwtAuthenticationFilter.class);

        return http.build();
    }

}

