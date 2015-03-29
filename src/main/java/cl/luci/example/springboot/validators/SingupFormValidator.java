package cl.luci.example.springboot.validators;

import cl.luci.example.springboot.dto.SignupForm;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ParameterNameProvider;
import javax.validation.executable.ExecutableValidator;

/**
 * @author Oreste Luci
 */
public class SingupFormValidator extends LocalValidatorFactoryBean {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignupForm.class);
    }

    @Override
    public void validate(Object target, Errors errors, final Object... validationHints) {
        //super.validate(target, errors);
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
