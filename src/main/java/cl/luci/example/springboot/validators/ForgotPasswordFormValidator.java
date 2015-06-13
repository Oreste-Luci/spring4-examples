package cl.luci.example.springboot.validators;

import cl.luci.example.springboot.dto.ForgotPasswordForm;
import cl.luci.example.springboot.dto.SignupForm;
import cl.luci.example.springboot.entities.User;
import cl.luci.example.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ParameterNameProvider;
import javax.validation.executable.ExecutableValidator;

/**
 * @author Oreste Luci
 */
@Component
public class ForgotPasswordFormValidator extends LocalValidatorFactoryBean {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignupForm.class);
    }

    @Override
    public void validate(Object target, Errors errors, final Object... validationHints) {

        super.validate(target, errors, validationHints);

        if (!errors.hasErrors()) {
            ForgotPasswordForm forgotPasswordForm = (ForgotPasswordForm)target;
            User user = userRepository.findByEmail(forgotPasswordForm.getEmail());
            if (user!=null) {
                errors.rejectValue("email","notfound");
            }
        }
    }

    @Override
    public ExecutableValidator forExecutables() {
        return null;
    }

    @Override
    public ParameterNameProvider getParameterNameProvider() {
        return null;
    }
}
