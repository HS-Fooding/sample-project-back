package hansung.com.sample_project.api;

import hansung.com.sample_project.dto.ReviewSimpleGetDto;
import hansung.com.sample_project.dto.ReviewPostDto;
import hansung.com.sample_project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value = "/review")
public class ReviewApiController {
    @Autowired
    ReviewService reviewService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReviewSimpleGetDto> postReview(HttpSession session,
                                                         @RequestPart ReviewPostDto reviewPostDto,
                                                         @RequestPart(value = "images", required = false) List<MultipartFile> images){
        return reviewService.postReview(session, reviewPostDto, images);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ReviewSimpleGetDto>> getReviews(){
        List<ReviewSimpleGetDto> reviewSimpleGetDtoList = reviewService.getReviews();
        return new ResponseEntity<>(reviewSimpleGetDtoList, HttpStatus.OK);
    }
}
