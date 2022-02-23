package hansung.com.sample_project.repository;

import hansung.com.sample_project.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    // 글 등록
    public void save(Review review) {
        em.persist(review);
    }

    // 글 찾기
    public Review findOne(Long id) {
        return em.createQuery("select r from Review r where review_id = :id", Review.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Review> getAll(){
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }
}
