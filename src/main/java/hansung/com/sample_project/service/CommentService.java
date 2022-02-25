package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.Comment;
import hansung.com.sample_project.domain.Review;
import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.dto.CommentDto;
import hansung.com.sample_project.dto.CommentPostDto;
import hansung.com.sample_project.dto.ReviewGetDto;
import hansung.com.sample_project.repository.CommentRepository;
import hansung.com.sample_project.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SecurityService securityService;

    // 댓글 등록
    @Transactional
    public ReviewGetDto postComment(Long reviewId, User author, CommentPostDto commentPostDto) {
        Review review = reviewRepository.findOne(reviewId);
        Comment comment = new Comment(review, author, commentPostDto);
        if(commentPostDto.getParent() != 0) {
            comment.setParent(commentRepository.findOne(commentPostDto.getParent()));
        }
        commentRepository.save(comment);
        return ReviewGetDto.from(review);
    }

    // 댓글 수정
    @Transactional
    public void modify(Long commentId) {
        Comment comment = commentRepository.findOne(commentId);
    }

    public List<Comment> findCommentByReview(Review review){
        return commentRepository.findByReview(review);
    }
}
