package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.Comment;
import hansung.com.sample_project.domain.Review;
import hansung.com.sample_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 등록
    @Transactional
    public void register(Comment comment) {
        commentRepository.save(comment);
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
