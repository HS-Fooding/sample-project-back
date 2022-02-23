package hansung.com.sample_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPostDto {
    private String title;
    private String content;
    private float star;
}
