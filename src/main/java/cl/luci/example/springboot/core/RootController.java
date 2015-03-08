package cl.luci.example.springboot.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Oreste Luci
 */
@Controller
public class RootController {

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
