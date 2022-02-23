package hansung.com.sample_project.api;

import hansung.com.sample_project.dto.ReviewGetDto;
import hansung.com.sample_project.dto.ReviewPostDto;
import hansung.com.sample_project.dto.ReviewSimpleGetDto;
import hansung.com.sample_project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;


@CrossOrigin("*")
@RestController
public class ReviewApiController {
    @Autowired ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<ReviewSimpleGetDto> postReview(HttpSession session,
                                @RequestPart(value = "review") ReviewPostDto reviewPostDto,
                                @RequestPart(value = "image", required = false) List<MultipartFile> images){
        return reviewService.postReview(session, reviewPostDto, images);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewSimpleGetDto>> getReviews(){
        List<ReviewSimpleGetDto> reviewSimpleGetDtoList = reviewService.getReviews();
        return new ResponseEntity<>(reviewSimpleGetDtoList, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewGetDto> getReview(@PathVariable Long reviewId) {
        System.out.println("########" + reviewId + "###############");
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }
}
