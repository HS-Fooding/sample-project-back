package hansung.com.sample_project.api;

import hansung.com.sample_project.service.UserService;
import lombok.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final UserService userService;

}
