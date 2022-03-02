package hansung.com.sample_project.api;

import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.dto.CommentPostDto;
import hansung.com.sample_project.dto.ReviewGetDto;
import hansung.com.sample_project.dto.ReviewPostDto;
import hansung.com.sample_project.dto.ReviewSimpleGetDto;
import hansung.com.sample_project.service.CommentService;
import hansung.com.sample_project.service.ReviewService;
import hansung.com.sample_project.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class ReviewApiController {
    @Autowired ReviewService reviewService;
    @Autowired SecurityService securityService;
    @Autowired CommentService commentService;

    @PostMapping("/review")
    public ResponseEntity<ReviewSimpleGetDto> postReview(HttpSession session,
                                @RequestPart(value = "review") ReviewPostDto reviewPostDto,
                                @RequestPart(value = "image", required = false) List<MultipartFile> images){
        return reviewService.postReview(session, reviewPostDto, images);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewSimpleGetDto>> getReviews(HttpSession httpSession, HttpServletRequest httpServletRequest){
        List<ReviewSimpleGetDto> reviewSimpleGetDtoList = reviewService.getReviews();
        System.out.println("IP : " + httpServletRequest.getRemoteAddr());
        System.out.println("Host : " + httpServletRequest.getRemoteHost());
        System.out.println("Port : " + httpServletRequest.getRemotePort());
        System.out.println("Session ID : " + httpServletRequest.getRequestedSessionId());
        return new ResponseEntity<>(reviewSimpleGetDtoList, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewGetDto> getReview(@PathVariable Long reviewId) {
        System.out.println("########" + reviewId + "###############");
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }

    @PostMapping("/review/{reviewId}/comment")
    public ResponseEntity<ReviewGetDto> postComment(@PathVariable(value = "reviewId") Long reviewId,
                                                    HttpSession session,
                                                    @RequestBody CommentPostDto commentPostDto){
        User author = securityService.getUser(session);
        if (author == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        ReviewGetDto reviewGetDto = commentService.postComment(reviewId, author, commentPostDto);
        return new ResponseEntity<>(reviewGetDto, HttpStatus.OK);
    }
}
