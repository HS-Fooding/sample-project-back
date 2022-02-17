package hansung.com.sample_project.repository;

import hansung.com.sample_project.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    // 저장
    public void save(User user) {
        em.persist(user);
    }

    // 단일 조회
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    // 이름으로 조회
    public User findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    // 모두 조회
    public List<User> findAll() {
        return em.createQuery("select m from Member m", User.class)
                .getResultList();
    }
}
