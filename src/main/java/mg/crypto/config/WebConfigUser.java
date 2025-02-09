package mg.crypto.config;

import mg.crypto.interceptor.SessionInterceptorUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigUser implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptorUser SessionInterceptorUser;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(SessionInterceptorUser)
                .addPathPatterns("/frontOffice/**");
    }
}
