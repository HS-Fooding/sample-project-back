package hansung.com.sample_project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(cascade=CascadeType.MERGE) @JoinTable(
            name="user_role",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private List<Role> roles;

    private String name;
    private int age;
    private boolean sex;

    @OneToMany(mappedBy = "author")
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Job job;

    @Enumerated(EnumType.STRING)
    private Favor favor;

    // 양방향 편의 메서드
    public void setReviews(Review review) {
        reviews.add(review);
        review.setAuthor(this);
    }

    public User(String userId, String email, String userPassword, String name, int age, Boolean sex) {
        this.userId = userId;
        this.email = email;
        this.userPassword = userPassword;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
