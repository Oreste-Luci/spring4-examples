package cl.luci.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@Configuration
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
public class SpringSampleApplication {

    public static final Logger logger = LoggerFactory.getLogger(SpringSampleApplication.class);

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(SpringSampleApplication.class,args);

        String beanNames[] = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        for (String beanName : beanNames) {
            logger.info("Bean: " + beanName);
        }
    }
}
