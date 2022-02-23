package hansung.com.sample_project.dto;

import hansung.com.sample_project.domain.Comment;
import hansung.com.sample_project.domain.Image;
import hansung.com.sample_project.domain.Review;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewGetDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private List<String> images = new ArrayList<>();
    private float star;
    private List<CommentDto> comments = new ArrayList<>();
    private String registerDate;
//    private String modifiedDate;

    public ReviewGetDto(Review review){
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id = review.getId();
        this.title = review.getTitle();
        this.author = review.getAuthor().getNickName();
        this.content = review.getContent();
        this.star= review.getStar();
//        this.registerDate = df.format(review.getTime().getRegisterLocalDateTime());
        this.registerDate = review.getTime().getRegisterLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        if(review.getImages() != null) {
            for (Image image : review.getImages())
                this.images.add(image.getPath());
        }
        if(review.getComments() != null) {
            for (Comment comment : review.getComments())
                this.comments.add(CommentDto.from(comment));
        }
    }

    public static ReviewGetDto from(Review review){
        return new ReviewGetDto(review);
    }
}
