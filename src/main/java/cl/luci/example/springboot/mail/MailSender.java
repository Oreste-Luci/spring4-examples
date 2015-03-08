package cl.luci.example.springboot.mail;

import javax.mail.MessagingException;

/**
 * @author Oreste Luci
 */
public interface MailSender {
    void send(String to, String subject, String body) throws MessagingException;
}
