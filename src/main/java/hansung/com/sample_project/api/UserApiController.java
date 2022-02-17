package hansung.com.sample_project.api;

import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/join")
    public JoinUserResponse joinUser(@RequestBody @Valid JoinUserRequest request) {
        User user = new User(request.getUserId(), request.getEmail(), request.getUserPassword(),
                        request.getName(), request.getAge(), request.getSex());
        Long id = userService.join(user);
        return new JoinUserResponse(id, request.getName());
    }

    @Data
    @Getter
    static class JoinUserRequest {
        @NotEmpty
        private String userId;

        @NotEmpty @Email
        private String email;

        @NotEmpty @Length(min = 3, max = 10)
        private String userPassword;

        @NotEmpty
        private String name;

        @NotEmpty
        private int age;

        private Boolean sex;
    }

    @Data
    @AllArgsConstructor
    static class JoinUserResponse {
        private Long id;
        private String name;

    }

}
