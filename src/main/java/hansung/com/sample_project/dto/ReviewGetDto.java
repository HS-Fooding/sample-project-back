package hansung.com.sample_project.dto;

import hansung.com.sample_project.domain.Comment;
import hansung.com.sample_project.domain.Image;
import hansung.com.sample_project.domain.Review;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewGetDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private List<String> images = new ArrayList<>();
    private float star;
    private List<CommentDto> commentDtos = new ArrayList<>();
    private String registerDate;
    private String modifiedDate;

    public ReviewGetDto(Review review){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id = review.getId();
        this.title = review.getTitle();
        this.author = review.getAuthor().getNickName();
        this.content = review.getContent();
        for(Image image: review.getImages())
            this.images.add(image.getPath());
        this.star= review.getStar();
        for(Comment comment : review.getComments()){
            this.commentDtos.add(CommentDto.from(comment));
        }
        this.registerDate = df.format(review.getTime().getRegisterLocalDateTime());
    }

    public static ReviewGetDto from(Review review){
        return new ReviewGetDto(review);
    }

}
