package cl.luci.example.springboot.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oreste Luci
 */
@RestController
public class HelloController {

    @Value("${app.name}")
    private String appName;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello from " + appName;
    }
}
