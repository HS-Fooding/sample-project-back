/*
package hansung.com.sample_project.security.config;

import hansung.com.sample_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    // BCryptPasswordEncordre는 Spring Security에서 제공하는 비밀번호 함호회 객체
    // 해시 함수를 이용해 패스워드를 암호화
    // 회원 비밀번호 등록시 해당 메서드를 이용하여 암호화해야, 로그인 처리시 동일한 해시로 비교함
    @Bean
    public BCryptPasswordEncoder encryptPassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(encryptPassword());
    }
}
*/
