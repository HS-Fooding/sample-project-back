package hansung.com.sample_project.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import java.util.Collections;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
//                .allowedOriginPatterns("**")
                .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000", "http://13.124.207.219:3000")
                .allowedMethods("HEAD","GET","POST","PUT","DELETE","PATCH")
                .allowCredentials(true)
                .maxAge(3600);
    }

    //j-session 삭제
    @Bean
    public ServletContextInitializer clearJsession() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
                SessionCookieConfig sessionCookieConfig=servletContext.getSessionCookieConfig();
                sessionCookieConfig.setHttpOnly(true);
            }
        };
    }
}
