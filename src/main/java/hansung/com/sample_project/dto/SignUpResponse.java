package hansung.com.sample_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@AllArgsConstructor
public class SignUpResponse {
    private HttpStatus status;
}
