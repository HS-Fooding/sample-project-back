package hansung.com.sample_project.domain;

import hansung.com.sample_project.dto.ReviewPostDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Lob
    private String content;

    @OneToMany(mappedBy = "review")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "comment_review")
    private List<Comment> comments = new ArrayList<>();

    private float star;

    @Embedded
    private Time time;

    private int count;

    // 연관관계 편의 메서드
    public void addAuthor(User user) {
        this.author = user;
        author.getReviews().add(this);
    }

    public void addImages(Image... image) {
        for (Image i : image) {
            images.add(i);
        }
    }
}
