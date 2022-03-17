package uk.co.wh.sportsbook;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Sportsbook", version = "1.0.0", description = "Microservice to handle football scoreboards on sports events"))
public class SportsBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsBookApplication.class, args);
    }

}
