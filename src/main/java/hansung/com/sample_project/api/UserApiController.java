package hansung.com.sample_project.api;

import hansung.com.sample_project.config.filter.JwtFilter;
import hansung.com.sample_project.dto.SignInRequest;
import hansung.com.sample_project.dto.SignUpRequest;
import hansung.com.sample_project.dto.SignUpResponse;
import hansung.com.sample_project.dto.TokenResponse;
import hansung.com.sample_project.exception.UserEmailAlreadyExistsException;
import hansung.com.sample_project.exception.UserIdExistsException;
import hansung.com.sample_project.exception.UserNickNameExistsException;
import hansung.com.sample_project.provider.JwtTokenProvider;
import hansung.com.sample_project.service.CustomUserDetailsService;
import hansung.com.sample_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/join")
    public SignUpResponse join(@RequestBody @Valid SignUpRequest request)
            throws UserIdExistsException, UserEmailAlreadyExistsException, UserNickNameExistsException {
        System.out.println("###############JOIN############");
        userService.join(request);

        return new SignUpResponse(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid SignInRequest signInRequest) {
        // userId와 userPassword로 authentication 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInRequest.getUserId(), signInRequest.getUserPassword());

        // authenticate를 실행할 때, loadUserByUsername 메소드 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        
        // authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenResponse(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)

    public void logout(HttpSession session) {
        // 세션 해제
        session.setAttribute("loginInfo", null);
        //
    }

    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public HttpSession hello(HttpSession session) {
        /*if (session.getAttribute("userDetails") != null) {
            UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
            System.out.println("1 " + session.getAttribute("userDetails"));
            System.out.println("2 " + userDetails.getUsername());
            System.out.println("3 " + userDetails.getPassword());

            return HttpStatus.OK;
        } else return HttpStatus.BAD_REQUEST;*/
        HttpSession result = (HttpSession) session.getAttribute("Set-Cookie");
        System.out.println(result);
        System.out.println(session.getAttributeNames().asIterator());
        return result;
    }
}
