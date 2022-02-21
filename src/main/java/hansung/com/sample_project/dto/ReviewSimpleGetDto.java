package hansung.com.sample_project.dto;

import hansung.com.sample_project.domain.Comment;
import hansung.com.sample_project.domain.Image;
import hansung.com.sample_project.domain.Review;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewSimpleGetDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private List<String> images = new ArrayList<>();
    private float star;
    private String registerDate;

    public ReviewSimpleGetDto(Review review){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id = review.getId();
        this.title = review.getTitle();
        this.author = review.getAuthor().getNickName();
        this.content = review.getContent();
        if(review.getImages() != null) {
            for (Image image : review.getImages())
                this.images.add(image.getPath());
        }
        this.star= review.getStar();
        this.registerDate = review.getTime().getRegisterLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static ReviewSimpleGetDto from(Review review){
        return new ReviewSimpleGetDto(review);
    }
}
