package hansung.com.sample_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponse {
//    private String accessToken;
//    private String refreshToken;
    private String userId;
    private String role;
}
