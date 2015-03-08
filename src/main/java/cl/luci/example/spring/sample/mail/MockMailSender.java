package cl.luci.example.spring.sample.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Oreste Luci
 */
public class MockMailSender implements MailSender {

    public static final Log log = LogFactory.getLog(MockMailSender.class);

    @Override
    public void send(String to, String subject, String body) {
        log.info("To: " + to);
        log.info("Subject: " + subject);
        log.info("Body: " + body);
    }
}
