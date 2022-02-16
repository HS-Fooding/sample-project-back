package hansung.com.sample_project.domain;


import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Image {

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;
    private String path;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
