package hansung.com.sample_project.domain;

import hansung.com.sample_project.dto.CommentDto;
import hansung.com.sample_project.dto.CommentPostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Lob
    private String content;

    @Embedded
    private Time time;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review comment_review;

    @ManyToOne
    @JoinColumn(name = "id")
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();

    public Comment(Review review, User author, CommentPostDto commentPostDto){
        this.content = commentPostDto.getContent();
        this.time = new Time();
        this.author = author;
        this.parent = this;
        addReview(review);
    }

    public void setParent(Comment comment) {
        this.parent = comment;
    }

    // ## 양방향 연관관계에 대한 편의 메서드 ## //
    public void addReview(Review review) {
        this.comment_review = review;
        review.getComments().add(this);
    }

    public void addChildCategory(Comment child) {
        this.child.add(child);
        child.setParent(this);
    }
}
