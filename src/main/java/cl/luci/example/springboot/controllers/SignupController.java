package cl.luci.example.springboot.controllers;

import cl.luci.example.springboot.dto.ForgotPasswordForm;
import cl.luci.example.springboot.dto.ResetPasswordForm;
import cl.luci.example.springboot.dto.SignupForm;
import cl.luci.example.springboot.services.UserService;
import cl.luci.example.springboot.utils.AppUtil;
import cl.luci.example.springboot.validators.ForgotPasswordFormValidator;
import cl.luci.example.springboot.validators.ResetPasswordFormValidator;
import cl.luci.example.springboot.validators.SingupFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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
    private ForgotPasswordFormValidator forgotPasswordFormValidator;

    @Autowired
    private ResetPasswordFormValidator resetPasswordFormValidator;

    @Autowired
    private UserService userService;

    @InitBinder("signupForm")
    protected void initSignupBinder(WebDataBinder binder) {
        binder.setValidator(signupFormValidator);
    }

    @InitBinder("forgotPasswordFrom")
    protected void initForgotPasswordBinder(WebDataBinder binder) {
        binder.setValidator(forgotPasswordFormValidator);
    }

    @InitBinder("resetPasswordForm")
    protected void initResetPasswordBinder(WebDataBinder binder) {
        binder.setValidator(resetPasswordFormValidator);
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

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String forgotPassword(Model model) {
        model.addAttribute(new ForgotPasswordForm());
        return "forgot-password";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String forgotPassword(@ModelAttribute("forgotPasswordForm") @Valid ForgotPasswordForm forgotPasswordForm,
                                 BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "forgot-password";
        }

        userService.forgotPassword(forgotPasswordForm);

        AppUtil.flash(redirectAttributes,"info","checkMailResetPassword");

        return "redirect:/";
    }

    /*
    * Reset password
    */
    @RequestMapping(value = "/reset-password/{forgotPasswordCode}")
    public String resetPassword(@PathVariable("forgotPasswordCode") String forgotPasswordCode, Model model) {

        model.addAttribute(new ResetPasswordForm());
        return "reset-password";

    }

    @RequestMapping(value = "/reset-password/{forgotPasswordCode}",
            method = RequestMethod.POST)
    public String resetPassword(
            @PathVariable("forgotPasswordCode") String forgotPasswordCode,
            @ModelAttribute("resetPasswordForm") @Valid ResetPasswordForm resetPasswordForm,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        userService.resetPassword(forgotPasswordCode, resetPasswordForm, result);

        if (result.hasErrors())
            return "reset-password";

        AppUtil.flash(redirectAttributes, "success", "passwordChanged");

        return "redirect:/login";
    }

}
