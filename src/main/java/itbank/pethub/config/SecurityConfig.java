package itbank.pethub.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
;

@Configuration
  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity` http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
                // hasRole('ROLE_USER')")
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
                // hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        return http.build();
    }

}
