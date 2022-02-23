package hansung.com.sample_project.domain;

import hansung.com.sample_project.dto.SignUpRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(unique = true)
    private String email;

    private String userName;

    @Column(unique = true)
    private String nickName;

    private int age;
    private boolean sex;

    @OneToMany(mappedBy = "author")
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Job job;

    @Enumerated(EnumType.STRING)
    private Favor favor;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setPasswordAndRole(String password, Role role) {
        this.userPassword = password;
        this.role = role;
    }


    public User(SignUpRequest request) {
        this.userId = request.getUserId();
        this.userPassword = request.getUserPassword();
        this.userName = request.getUserName();
        this.nickName = request.getNickName();
        this.sex = request.getSex();
        this.email = request.getEmail();
        this.age = request.getAge();
        this.role = request.getRole();
    }

    public User(String userId, String userPassword, String userName, String nickName, Boolean sex, String email, int age, Role role) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.sex = sex;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.age = age;
        this.role = role;
    }

    public void setReviews(Review review) {
        getReviews().add(review);
    }
    public void setUserPassword(String password) {
        this.userPassword = password;
    }
}
