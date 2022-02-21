package hansung.com.sample_project.repository;

import hansung.com.sample_project.domain.Comment;
import hansung.com.sample_project.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findAll(Long id) {
        return em.createQuery("select c from Comment c where id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Comment> findByReview(Review review){
        return em.createQuery("select c from Comment c where c.comment_review = : review", Comment.class)
                .setParameter("review", review)
                .getResultList();
    }
}
