package hansung.com.sample_project.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String password;
    private double star;
    private LocalDateTime createdAt;

    @Lob
    private String content;


    public Member() {}
    public Member(String name, String password, double star, String content) {
        this.name = name;
        this.password = password;
        this.star = star;
        this.content = content;
    }
}
