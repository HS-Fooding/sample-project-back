package hansung.com.sample_project.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Time {
    private LocalDateTime registerLocalDateTime;
    private LocalDateTime modifiedLocalDateTime;
}
