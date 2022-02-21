package hansung.com.sample_project.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class Time {
    private LocalDateTime registerLocalDateTime;
    private LocalDateTime modifiedLocalDateTime;

    public Time(){
        registerLocalDateTime = LocalDateTime.now();
        modifiedLocalDateTime = registerLocalDateTime;
    }
}
