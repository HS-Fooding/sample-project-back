package hansung.com.sample_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.com.sample_project.dto.ReviewPostDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @JsonIgnore
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

    public Review(ReviewPostDto reviewPostDto){
        this.title = reviewPostDto.getTitle();
        this.content = reviewPostDto.getContent();
        this.star = reviewPostDto.getStar();
        this.time = new Time();
        this.count = 0;
    }
}
