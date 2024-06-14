package itbank.pethub.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AuthorInterceptor authorInterceptor;

    @Autowired
    private ReplyInterceptor replyInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/board/write",
                        "/board/addReply");

        registry.addInterceptor(authorInterceptor)
                .addPathPatterns("/board/updateBd/**",
                        "/board/deleteBd/**");

        registry.addInterceptor(replyInterceptor)
                .addPathPatterns("/board/popUp/**",
                        "/board/deleteReply/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/board/write");
    }

}

