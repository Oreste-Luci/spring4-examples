package cl.luci.example.spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class SpringSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSampleApplication.class, args);
    }
}
