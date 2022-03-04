package hansung.com.sample_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                /*.allowedOrigins("http://1.224.241.195:3000", "http://124.49.33.39:3000",
                                    "http://localhost:3000", "http://127.0.0.1:3000",
                                    "http://192.168.219.106:3000")*/
                .allowedOriginPatterns("**")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
                .allowCredentials(true)
                .maxAge(3600);
    }
}