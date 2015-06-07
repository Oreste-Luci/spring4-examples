package cl.luci.example.springboot.services;

import cl.luci.example.springboot.dto.SignupForm;
import cl.luci.example.springboot.dto.UserDetailsImpl;
import cl.luci.example.springboot.entities.User;
import cl.luci.example.springboot.mail.MailSender;
import cl.luci.example.springboot.repositories.UserRepository;
import cl.luci.example.springboot.utils.AppUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.mail.MessagingException;

/**
 * @author Oreste Luci
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=false)
    public void signup(SignupForm signupForm) {

        User user = new User();
        user.setEmail(signupForm.getEmail());
        user.setName(signupForm.getName());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        user.getRoles().add(User.Role.UNVERIFIED);
        user.setVerificationCode(RandomStringUtils.randomAlphanumeric(16));
        userRepository.save(user);

        // Only executing if transaction committed
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        // Sending email
                        String verificationLink = AppUtil.hostURL() + "/users/" + user.getVerificationCode() + "/verify";
                        try {
                            mailSender.send(user.getEmail(),AppUtil.getMessage("verifySubject"),AppUtil.getMessage("verifyEmail",verificationLink));
                            logger.info("Verification email sent to " + user.getEmail());
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return new UserDetailsImpl(user);
    }
}
