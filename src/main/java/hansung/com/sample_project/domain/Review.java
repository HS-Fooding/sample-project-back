package hansung.com.sample_project.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @Lob
    private String content;

    @OneToMany(mappedBy = "review")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "comment_review")
    private List<Comment> comments = new ArrayList<>();

    private float star;

    @Embedded
    private Time time;
}
