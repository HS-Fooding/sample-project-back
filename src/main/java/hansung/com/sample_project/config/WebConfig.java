package hansung.com.sample_project.config;

import hansung.com.sample_project.config.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new LoginCheckFilter());
        filterRegistration.setOrder(1); // 필터 체인 순서
        filterRegistration.addUrlPatterns("/*"); // 모든 요청에 대해 필터 적용

        return filterRegistration;
    }
}
