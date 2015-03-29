package cl.luci.example.springboot.services;

import cl.luci.example.springboot.dto.SignupForm;

/**
 * @author Oreste Luci
 */
public interface UserService {

    public abstract void signup(SignupForm signupForm);
}
