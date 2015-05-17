package cl.luci.example.springboot.controllers;

import cl.luci.example.springboot.dto.SignupForm;
import cl.luci.example.springboot.services.UserService;
import cl.luci.example.springboot.utils.AppUtil;
import cl.luci.example.springboot.validators.SingupFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Oreste Luci
 */
@Controller
public class SignupController {

    public static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private SingupFormValidator signupFormValidator;

    @Autowired
    private UserService userService;

    @InitBinder("signupForm")
    protected void initSignupBinder(WebDataBinder binder) {
        binder.setValidator(signupFormValidator);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {

        //model.addAttribute("signupForm", new SignupForm());
        model.addAttribute(new SignupForm());

        return "signup";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute("signupForm") @Valid SignupForm signupForm, BindingResult result,RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "signup";
        }

        logger.info(signupForm.toString());

        userService.signup(signupForm);

        AppUtil.flash(redirectAttributes,"success","message.signupSuccess");

        return "redirect:/";
    }
}
