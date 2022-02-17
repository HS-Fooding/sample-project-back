package hansung.com.sample_project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="roles")
@Getter
@NoArgsConstructor
public class Role {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=true)
    private String roleName;

    @ManyToMany(mappedBy="roles")
    private List<Member> members;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
