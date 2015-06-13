package cl.luci.example.springboot.services;

import cl.luci.example.springboot.dto.ForgotPasswordForm;
import cl.luci.example.springboot.dto.ResetPasswordForm;
import cl.luci.example.springboot.dto.SignupForm;
import org.springframework.validation.BindingResult;

/**
 * @author Oreste Luci
 */
public interface UserService {

    public abstract void signup(SignupForm signupForm);

    public abstract void verify(String verificationCode);

    void forgotPassword(ForgotPasswordForm forgotPasswordForm);

    void resetPassword(String forgotPasswordCode, ResetPasswordForm resetPasswordForm, BindingResult result);
}
