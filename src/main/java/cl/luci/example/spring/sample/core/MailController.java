package cl.luci.example.spring.sample.core;

import cl.luci.example.spring.sample.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * @author Oreste Luci
 */
@RestController
public class MailController {

    @Autowired
    private MailSender mailSender;

    @RequestMapping("/mail")
    public String sendMail() throws MessagingException {

        mailSender.send("xxx@mail.com","Test Subject","Test Content");

        return "Mail Sent!!!";
    }
}
