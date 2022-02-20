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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/hello/join")
    public SignUpResponse join(@RequestBody @Valid SignUpRequest request)
            throws UserIdExistsException, UserEmailAlreadyExistsException, UserNickNameExistsException {
        System.out.println("###############JOIN############");
        userService.join(request);

        return new SignUpResponse(HttpStatus.OK);
    }

    @GetMapping("/hello/join")
    public String getmapping() {
        return "hello";
    }

    @PostMapping("/hello/login")
    public HttpStatus login(@RequestBody @Valid SignInRequest request)
            throws LoginFailureException {
        System.out.println("###############LOG-IN############");

        return HttpStatus.OK;
    }
}
