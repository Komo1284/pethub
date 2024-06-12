package itbank.pethub.config;


import jakarta.servlet.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
public class SecurityConfig extends WebSecurityConfiguration {

    @Override
    public Filter springSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers("/member/login").permitAll()
                .anyRequest().authenticated();

        http.build();
    }
}
