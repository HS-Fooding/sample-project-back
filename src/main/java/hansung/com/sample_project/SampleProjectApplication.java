package hansung.com.sample_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleProjectApplication.class, args);
    }
}

/*@SpringBootApplication
public class SampleProjectApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SampleProjectApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleProjectApplication.class, args);
    }

}*/
