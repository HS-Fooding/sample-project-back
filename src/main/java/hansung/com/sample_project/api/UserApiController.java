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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@CrossOrigin("*")
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
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody @Valid SignInRequest signInRequest, HttpSession session)
            throws LoginFailureException {
        System.out.println("###############LOG-IN############");

        userService.validatePassword(signInRequest);

        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUserId());

        // TODO : 로그인에 대한 세션 처리 (세션은 서버에서 관리)
        session.setAttribute("userDetails", userDetails);
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
        if(session.getAttribute("userDetails") != null) {
            UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
            System.out.println("1 " + session.getAttribute("userDetails"));
            System.out.println("2 " + userDetails.getUsername());
            System.out.println("3 " + userDetails.getPassword());

            return HttpStatus.OK;
        }
        else return HttpStatus.BAD_REQUEST;
    }
}
