package hansung.com.sample_project.dto;

import hansung.com.sample_project.domain.Comment;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentDto {
    private Long id;
    private Long parent = 0L;
    private String content;
    private String author;
    private String registerDate;
    private String modifiedDate;

    public CommentDto(Comment comment){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id = comment.getId();
        if(comment.getParent() != null) this.parent = comment.getParent().getId();
        this.content = comment.getContent();
        this.author= comment.getAuthor().getNickName();
        //this.registerDate = df.format(comment.getTime().getRegisterLocalDateTime());
        this.registerDate = comment.getTime().getRegisterLocalDateTime().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        this.modifiedDate = comment.getTime().getModifiedLocalDateTime().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));

    }

    public static CommentDto from(Comment comment){
        return new CommentDto(comment);
    }
}
