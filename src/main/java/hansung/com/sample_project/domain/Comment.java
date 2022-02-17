package hansung.com.sample_project.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();

    // ## 양방향 연관관계에 대한 편의 메서드 ## //
    public void addChildCategory(Comment child) {
        this.child.add(child);
        child.setParent(this);
    }

    public void setParent(Comment comment) {
        this.parent = comment;
    }

    public enum Favor {
        KOR, JAP, US, SNACK
    }

    public enum Job {
        SERVICE, STUDENT, EDUCATE
    }
}
