package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.Image;
import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.domain.Review;
import hansung.com.sample_project.repository.ImageRepository;
import hansung.com.sample_project.repository.UserRepository;
import hansung.com.sample_project.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    // 글 등록
    @Transactional
    public Long register(Long memberId, Review review, Image... images) {
        User member = userRepository.findById(memberId);
        review.setImages(images);
        member.getReviews().add(review);

        return review.getId();
    }

    // 특정 사용자가 쓴 리뷰 조회
    public List<Review> findReviews(Long memberId) {
        return reviewRepository.findOne(memberId);
    }

    public String saveImage(MultipartFile image) {
        String fileName = "";

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
                folderRoot = "/capstone/sample_project_jhs/"; //리눅스 경로
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
            fileName = getNowTime24()  + "_" + String.valueOf(image.getOriginalFilename());
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

        return fileName;
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
