package cl.luci.example.springboot.core;

import cl.luci.example.springboot.dto.SignupForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {

        //model.addAttribute("signupForm", new SignupForm());
        model.addAttribute(new SignupForm());

        return "signup";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute("signupForm") SignupForm signupForm) {

        logger.info(signupForm.toString());

        return "redirect:/";
    }
}
