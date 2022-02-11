package hansung.com.sample_project.api;

import hansung.com.sample_project.domain.Member;
import hansung.com.sample_project.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/members")
    public Result members() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m->new MemberDto(m.getName(), m.getStar(), m.getContent(), m.getImage()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member(request.getName(), request.getPassword(), request.getStar(), request.getContent(), request.getImage());
        Long id = memberService.join(member);

        return new CreateMemberResponse(id, request.getName());
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        private double star;
        private String content;
        private String image;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;

        @NotEmpty
        private String password;

        private double star;

        private String content;
        private String image;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;
        private String name;

        public CreateMemberResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
