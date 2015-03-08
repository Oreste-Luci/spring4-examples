package cl.luci.example.spring.sample.mail;

import javax.mail.MessagingException;

/**
 * @author Oreste Luci
 */
public interface MailSender {
    void send(String to, String subject, String body) throws MessagingException;
}
