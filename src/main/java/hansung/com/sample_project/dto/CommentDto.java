package hansung.com.sample_project.dto;

import hansung.com.sample_project.domain.Comment;

import java.text.SimpleDateFormat;

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
        this.author= comment.getContent();
        this.registerDate = df.format(comment.getTime().getRegisterLocalDateTime());
        this.modifiedDate=df.format(comment.getTime().getModifiedLocalDateTime());
    }

    public static CommentDto from(Comment comment){
        return new CommentDto(comment);
    }
}
