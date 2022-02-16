package hansung.com.sample_project.api;

import hansung.com.sample_project.service.MemberService;
import lombok.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

}
