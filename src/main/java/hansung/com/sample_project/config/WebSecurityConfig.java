package hansung.com.sample_project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

//    @Autowired
//    private UserDetailsService userDetailsService;

    private AuthenticationProvider authenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authentication
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override // 스프링 시큐리티 룰을 무시하게 하는 URL 규칙
    public void configure(WebSecurity web) throws Exception {
        logger.info("#################");

        web.ignoring().antMatchers("/hello/hi");
    }

    // authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("#################");
        http
                .authorizeRequests()
                .antMatchers("/hello").access("hasRole('ROLE_USER')")
                .antMatchers("/good").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable();
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

//                .csrf().disable();

    }

//    private static final String[] PUBLIC_MATCHERS = {
//            "/webjars/**",
//            "/css/**",
//            "/js/**",
//            "/images/**",
//            "/about/**",
//            "/contact/**",
//            "/error/**/*",
//            "/console/**"
//    };

}