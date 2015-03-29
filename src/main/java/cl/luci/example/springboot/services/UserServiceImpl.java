package cl.luci.example.springboot.services;

import cl.luci.example.springboot.dto.SignupForm;
import cl.luci.example.springboot.entities.User;
import cl.luci.example.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Oreste Luci
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void signup(SignupForm signupForm) {

        User user = new User();
        user.setEmail(signupForm.getEmail());
        user.setName(signupForm.getName());
        user.setPassword(signupForm.getPassword());

        userRepository.save(user);
    }
}
