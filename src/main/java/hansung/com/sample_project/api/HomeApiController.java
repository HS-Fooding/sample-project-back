package hansung.com.sample_project.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApiController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
