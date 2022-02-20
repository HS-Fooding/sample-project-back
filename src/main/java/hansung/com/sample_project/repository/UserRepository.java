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
    public User findByUserName(String userName) {
        return em.createQuery("select u from User u where u.userName = :userName", User.class)
                .setParameter("userName", userName)
                .getSingleResult();
    }
    // 아이디로 조회
    public User findByUserId(String userId) {
        return em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
    
    public Boolean existByUserId(String userId) {
        try {
            User user = em.createQuery("select u from User u where u.userId = :userId", User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            if(user.getUserId() != null)
                return true;
            else return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean existByEmail(String email) {
        try {
            User user = em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if(user.getEmail() != null)
                return true;
            else return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean existByNickName(String nickName) {
        try {
            User user = em.createQuery("select u from User u where u.nickName = :nickName", User.class)
                    .setParameter("nickName", nickName)
                    .getSingleResult();
            if(user.getNickName() != null)
                return true;
            else return false;
        } catch (Exception e) {
            return false;
        }
    }

    // 모두 조회
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
