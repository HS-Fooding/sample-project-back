package hansung.com.sample_project.api;

import hansung.com.sample_project.domain.Member;
import hansung.com.sample_project.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/sample_project/members")
    public Result members() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getStar(), m.getContent()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping(value = "/sample_project/members")
    public CreateMemberResponse saveMember(@RequestPart @Valid CreateMemberRequest request,
                                           @RequestPart MultipartFile image) {
        Member member = new Member(request.getName(), request.getPassword(),
                request.getStar(), request.getContent());
        Long id = memberService.join(member);

        // [서버 로컬에 이미지 저장 로직 수행 실시]
        try {
            // 시스템 os 정보 확인 변수 선언
            String os = System.getProperty("os.name").toLowerCase();

            // 사진을 저장할 폴더 경로 변수 선언
            String folderRoot = "";

            // File 객체 정의
            File file;

            // os 정보 확인 및 사진을 저장할 서버 로컬 경로 지정 실시
            if (os.contains("win")) {
                System.out.println("\n");
                System.out.println("=======================================");
                System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
                System.out.println("[os] : " + "Windows");
                System.out.println("=======================================");
                System.out.println("\n");
//                folderRoot = "c:/Home/Resource/assets/"; //윈도우 경로 (디스크 필요)
                folderRoot = "C:/Users/bs860/IdeaProjects/images/"; //윈도우 경로 (디스크 필요)

            } else if (os.contains("linux")) {
                System.out.println("\n");
                System.out.println("=======================================");
                System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
                System.out.println("[os] : " + "Linux");
                System.out.println("=======================================");
                System.out.println("\n");
//                folderRoot = "/Home/Resource/assets/"; //리눅스 경로
                folderRoot = "/home/1791101/images/"; //리눅스 경로
            } else {
                System.out.println("\n");
                System.out.println("=======================================");
                System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
                System.out.println("[os] : " + "None");
                System.out.println("=======================================");
                System.out.println("\n");
                // os 확인을 하지 못한 경우 즉시, api 종료
            }
            // 파일 객체 생성 및 폴더 생성 여부 확인 수행
            file = new File(folderRoot);

            if (!file.exists()) { //폴더가 존재하지 않는 경우
                file.mkdirs(); //폴더 생성 수행
                System.out.println("\n");
                System.out.println("=======================================");
                System.out.println("[ModuleApiController] : [resourceInsertImage] : [folder check]");
                System.out.println("[folder] : " + "not exists");
                System.out.println("[folder] : " + "create");
                System.out.println("=======================================");
                System.out.println("\n");
            } else { //폴더가 존재하는 경우
                System.out.println("\n");
                System.out.println("=======================================");
                System.out.println("[ModuleApiController] : [resourceInsertImage] : [folder check]");
                System.out.println("[folder] : " + "exists");
                System.out.println("=======================================");
                System.out.println("\n");
            }

            // 파일명에 현재 연도,월,일,시,분,초 + 이미지 파일명 조합 실시 및 서버 로컬에 이미지 저장 수행
            String fileName = getNowTime24() + "_" + String.valueOf(image.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(folderRoot + fileName);

            byte data[] = image.getBytes();
            InputStream inputStream = image.getInputStream();

            int readCount = 0;
            while ((readCount = inputStream.read(data)) != -1) {
                fos.write(data, 0, readCount);
            }
            fos.close();
            inputStream.close();

            // 모든 로직 처리가 정상 완료 된 경우
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [image save]");
            System.out.println("[result] : " + "Success");
            System.out.println("[fileRoot] : " + (folderRoot + fileName));
            System.out.println("=======================================");
            System.out.println("\n");
        } catch (Exception e) {
            // 로직 처리 중 예외가 발생한 경우
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [image save]");
            System.out.println("[result] : " + "Fail");
            System.out.println("=======================================");
            System.out.println("\n");

            e.printStackTrace();
        }

        return new CreateMemberResponse(id, request.getName());
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
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
    // [현재 시간 알아오는 메소드]
    public static String getNowTime24() {
        long time = System.currentTimeMillis();
        //SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddkkmmss");
        String str = dayTime.format(new Date(time));
        return "PT"+str; //TODO [PT는 picture 의미]
    }
}
