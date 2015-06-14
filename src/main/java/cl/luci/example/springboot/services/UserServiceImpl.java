package cl.luci.example.springboot.services;

import cl.luci.example.springboot.dto.*;
import cl.luci.example.springboot.entities.User;
import cl.luci.example.springboot.mail.MailSender;
import cl.luci.example.springboot.repositories.UserRepository;
import cl.luci.example.springboot.utils.AppUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import org.springframework.validation.BindingResult;

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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void verify(String verificationCode) {

        // User should be logged in. See WebSecurityConfig
        long loggedInUserId = AppUtil.getSessionUser().getId();
        User user = userRepository.findOne(loggedInUserId);

        AppUtil.validate(user.getRoles().contains(User.Role.UNVERIFIED),"alreadyVerified");

        AppUtil.validate(user.getVerificationCode().equals(verificationCode),"incorrect","verification code");

        user.getRoles().remove(User.Role.UNVERIFIED);
        user.setVerificationCode(null);
        userRepository.save(user);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void forgotPassword(ForgotPasswordForm form) {

        final User user = userRepository.findByEmail(form.getEmail());
        final String forgotPasswordCode = RandomStringUtils.randomAlphanumeric(User.RANDOM_CODE_LENGTH);

        user.setForgotPasswordCode(forgotPasswordCode);
        final User savedUser = userRepository.save(user);

        // Only send email if transaction successful
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        try {
                            mailForgotPasswordLink(savedUser);
                        } catch (MessagingException e) {
                            logger.error(ExceptionUtils.getStackTrace(e));
                        }
                    }

                });

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void resetPassword(String forgotPasswordCode,
                              ResetPasswordForm resetPasswordForm,
                              BindingResult result) {

        User user = userRepository.findByForgotPasswordCode(forgotPasswordCode);

        if (user == null)
            result.reject("invalidForgotPassword");

        if (result.hasErrors())
            return;

        user.setForgotPasswordCode(null);
        user.setPassword(passwordEncoder.encode(resetPasswordForm.getPassword().trim()));
        userRepository.save(user);
    }

    @Override
    public User findOne(long userId) {

        User loggedIn = AppUtil.getSessionUser();
        User user = userRepository.findOne(userId);

        if (loggedIn == null || (loggedIn.getId() != user.getId() && !loggedIn.isAdmin())) {

            // Hide email
            user.setEmail("Confidential");
        }

        return user;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void update(long userId, UserEditForm userEditForm) {

        User loggedIn = AppUtil.getSessionUser();
        AppUtil.validate(loggedIn.isAdmin() || loggedIn.getId() == userId, "noPermissions");
        User user = userRepository.findOne(userId);
        user.setName(userEditForm.getName());
        if (loggedIn.isAdmin())
            user.setRoles(userEditForm.getRoles());
        userRepository.save(user);
    }

    private void mailForgotPasswordLink(User user) throws MessagingException {

        String forgotPasswordLink = AppUtil.hostUrl() + "/reset-password/" + user.getForgotPasswordCode();
        mailSender.send(user.getEmail(),
                AppUtil.getMessage("forgotPasswordSubject"),
                AppUtil.getMessage("forgotPasswordEmail", forgotPasswordLink));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return new UserDetailsImpl(user);
    }
}
