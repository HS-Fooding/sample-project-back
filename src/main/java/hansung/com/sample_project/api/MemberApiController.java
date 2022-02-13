package hansung.com.sample_project.api;

import hansung.com.sample_project.domain.Member;
import hansung.com.sample_project.service.MemberService;
import lombok.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/members/content")
    public MemberDto showMember(@RequestBody @Valid getAMemberRequest request) throws IOException {
        Member member = memberService.findMember(Long.parseLong(request.getId()));
//        String path = "C:/Users/bs860/IdeaProjects/images/" + member.getFileName();
        String path= "/capstone/sample_project_jhs/" + member.getFileName(); //리눅스 경로


        Resource resource = new FileSystemResource(path);

        if(!resource.exists()) // TODO: 파일이 존재하지 않으면 404 Error
            System.out.println("ERROR");

        HttpHeaders header = new HttpHeaders();
        Path filePath = null;

        filePath = Paths.get(path); // file의 경로를 구함
//        header.add("Content-Type", Files.probeContentType(filePath));
        header.add("Content-Type", "multipart/form-data");

        return new MemberDto(member.getName(), member.getStar(), member.getContent(), header, resource, HttpStatus.OK);
    }

    @Data
    @Getter @Setter
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        private double star;
        private String content;
        private HttpHeaders header;
        private Resource resource;
        private HttpStatus status;
    }

    @Data
    static class getAMemberRequest {
        @NotEmpty String id;
        @NotEmpty String password;
    }


    @GetMapping("/members")
    public Result members() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberListDto> collect = findMembers.stream()
                .map(m -> new MemberListDto(m.getId(), m.getName(), m.getStar(), m.getContent()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/members")
    public CreateMemberResponse saveMember(@RequestPart @Valid CreateMemberRequest request,
                                           @RequestPart MultipartFile image) {
        String fileName = memberService.saveImage(image);
        Member member = new Member(request.getName(), request.getPassword(),
                request.getStar(), request.getContent(), fileName);
        Long id = memberService.join(member);

        return new CreateMemberResponse(id, request.getName());
    }


    @Data
    @AllArgsConstructor
    static class MemberListDto {
        Long id;
        private String name;
        private double star;
        private String content;
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
