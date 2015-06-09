package cl.luci.example.springboot.controllers;

/**
 * @author Oreste Luci
 */

import cl.luci.example.springboot.services.UserService;
import cl.luci.example.springboot.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users/{verificationCode}/verify")
    public String verify(@PathVariable("verificationCode") String verificationCode,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) throws ServletException {

        userService.verify(verificationCode);

        AppUtil.flash(redirectAttributes,"success","verificationSuccess");

        request.logout();

        return "redirect:/";
    }
}
