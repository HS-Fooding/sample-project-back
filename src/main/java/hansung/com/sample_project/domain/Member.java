package hansung.com.sample_project.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String user_id;
    private String user_password;

    private String name;
    private int age;
    private boolean sex;

    @OneToMany(mappedBy = "author")
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Job job;

    @Enumerated(EnumType.STRING)
    private Favor favor;
}
