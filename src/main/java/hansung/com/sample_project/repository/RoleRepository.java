package hansung.com.sample_project.repository;

import hansung.com.sample_project.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByRolename(String roleName);
}
