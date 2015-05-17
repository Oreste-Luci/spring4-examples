package cl.luci.example.springboot.services;

import cl.luci.example.springboot.dto.SignupForm;
import cl.luci.example.springboot.dto.UserDetailsImpl;
import cl.luci.example.springboot.entities.User;
import cl.luci.example.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oreste Luci
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=false)
    public void signup(SignupForm signupForm) {

        User user = new User();
        user.setEmail(signupForm.getEmail());
        user.setName(signupForm.getName());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return new UserDetailsImpl(user);
    }
}
