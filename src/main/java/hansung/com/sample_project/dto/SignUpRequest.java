package hansung.com.sample_project.dto;

import hansung.com.sample_project.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {

    String userId;
    String userPassword;
    String userName;
    String nickName;
    Boolean sex;
    String email;
    int age;
    Role role;
}
