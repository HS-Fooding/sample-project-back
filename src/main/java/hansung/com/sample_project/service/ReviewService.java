package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.Image;
import hansung.com.sample_project.domain.Time;
import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.domain.Review;
import hansung.com.sample_project.dto.ReviewGetDto;
import hansung.com.sample_project.dto.ReviewSimpleGetDto;
import hansung.com.sample_project.dto.ReviewPostDto;
import hansung.com.sample_project.dto.UserInfo;
import hansung.com.sample_project.handler.ImageHandler;
import hansung.com.sample_project.repository.ImageRepository;
import hansung.com.sample_project.repository.UserRepository;
import hansung.com.sample_project.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        review.addImages(images);
        member.getReviews().add(review);

        return review.getId();
    }

//    // 특정 사용자가 쓴 리뷰 조회
//    public List<Review> findReviews(Long memberId) {
//        return reviewRepository.findOne(memberId);
//    }

    public ReviewGetDto getReview(Long id){
        Review review = reviewRepository.findOne(id);
        return ReviewGetDto.from(review);
    }

    public List<ReviewSimpleGetDto> getReviews(){
        List<Review> reviews = reviewRepository.getAll();
        List<ReviewSimpleGetDto> reviewSimpleGetDtoList = new ArrayList<>();
        for(Review review: reviews){
            reviewSimpleGetDtoList.add(ReviewSimpleGetDto.from(review));
        }
        return reviewSimpleGetDtoList;
    }

    @Transactional
    public ResponseEntity<ReviewSimpleGetDto> postReview(HttpSession session, ReviewPostDto reviewPostDto, List<MultipartFile> images) {
//        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
//        if(userInfo == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//        User author = userRepository.findById(userInfo.getId());
        User author = new User();
        author.setUserId("testId1");
        author.setUserPassword("1234");
        author.setNickName("testNickname");
        author.setEmail("teset@test.test");
        userRepository.save(author);
        List<String> imagePaths = ImageHandler.upload(images);
        List<Image> savedImages = new ArrayList<>();

//        Review review = Review.builder()
//                .author(author)
//                .title(reviewPostDto.getTitle())
//                .content(reviewPostDto.getContent())
//                .star(reviewPostDto.getStar())
//                .time(new Time())
//                .count(0)
//                .build();
        Review review = new Review(reviewPostDto);
        review.setAuthor(author);

        reviewRepository.save(review);


        for(String imagePath : imagePaths){
            Image savedImage = new Image();
            savedImage.setPath(imagePath);
            savedImage.setReview(review);
            savedImages.add(savedImage);
        }
        review.setImages(savedImages);
        imageRepository.saveImages(savedImages);
        return new ResponseEntity<>(ReviewSimpleGetDto.from(review), HttpStatus.OK);
    }
}
