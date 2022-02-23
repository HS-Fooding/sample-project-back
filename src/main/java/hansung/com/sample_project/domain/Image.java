package hansung.com.sample_project.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Image {

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;
    private String path;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    // 연관관계 편의 메서드 
    public void setReview(Review review) {
        this.review = review;
        //review.getImages().add(this);
    }
}
