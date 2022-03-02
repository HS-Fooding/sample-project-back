package hansung.com.sample_project.api;

import hansung.com.sample_project.dto.SignInRequest;
import hansung.com.sample_project.dto.SignUpRequest;
import hansung.com.sample_project.dto.SignUpResponse;
import hansung.com.sample_project.exception.LoginFailureException;
import hansung.com.sample_project.exception.UserEmailAlreadyExistsException;
import hansung.com.sample_project.exception.UserIdExistsException;
import hansung.com.sample_project.exception.UserNickNameExistsException;
import hansung.com.sample_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/join")
    public SignUpResponse join(@RequestBody @Valid SignUpRequest request)
            throws UserIdExistsException, UserEmailAlreadyExistsException, UserNickNameExistsException {
        System.out.println("###############JOIN############");
        userService.join(request);

        return new SignUpResponse(HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpStatus login(@RequestBody @Valid SignInRequest signInRequest, HttpSession session,
                            HttpServletResponse response , HttpServletRequest httpServletRequest)
            throws LoginFailureException {
        System.out.println("###############LOG-IN############");

        userService.validatePassword(signInRequest);

//        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUserId());
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(userDetails.getUsername());
        // TODO : 로그인에 대한 세션 처리 (세션은 서버에서 관리)

//        Cookie cookie = new Cookie("userInfo", userInfo.getUserId());
//        cookie.setMaxAge(3600);
//        response.setHeader("Set-Cookie", "userInfo; SameSite=Strict; HttpOnly=false;");
//        response.addCookie(cookie);

        return HttpStatus.OK;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpSession session) {
        // 세션 해제
        session.setAttribute("loginInfo", null);
        //
    }

    @GetMapping("/hello")
    public HttpStatus hello(HttpSession session) {
        if (session.getAttribute("userDetails") != null) {
            UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
            System.out.println("1 " + session.getAttribute("userDetails"));
            System.out.println("2 " + userDetails.getUsername());
            System.out.println("3 " + userDetails.getPassword());

            return HttpStatus.OK;
        } else return HttpStatus.BAD_REQUEST;
    }
}
