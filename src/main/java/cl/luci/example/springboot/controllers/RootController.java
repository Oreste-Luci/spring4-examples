package cl.luci.example.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Oreste Luci
 */
@Controller
public class RootController {

    public static final Logger logger = LoggerFactory.getLogger(RootController.class);

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/nojsp")
    @ResponseBody
    public String noJsp() {

        return "Directly from controller!!";
    }
}
