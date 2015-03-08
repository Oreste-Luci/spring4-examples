package cl.luci.example.springboot.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author Oreste Luci
 */
@Configuration
public class MailConfiguration {

    @Bean
    @Profile("dev")
    public MailSender mockMailSender() {
        return new MockMailSender();
    }

    @Bean
    @Profile("!dev")
    //@Primary
    public MailSender smtpMailSender(JavaMailSender javaMailSender)  {

        SmtpMailSender smtpMailSender = new SmtpMailSender();
        smtpMailSender.setJavaMailSender(javaMailSender);

        return smtpMailSender;
    }
}
