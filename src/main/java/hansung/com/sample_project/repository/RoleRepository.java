package hansung.com.sample_project.repository;

import hansung.com.sample_project.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RoleRepository {

    private final EntityManager em;

    // 저장
    public void save(Role role) {
        em.persist(role);
    }

    // 단일 조회
    public Role findOne(Long id) {
        return em.find(Role.class, id);
    }
}
