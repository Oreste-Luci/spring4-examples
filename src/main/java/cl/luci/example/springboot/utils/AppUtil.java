package cl.luci.example.springboot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

/**
 * @author Oreste Luci
 */
@Component
public class AppUtil {

    private static MessageSource messageSource;
    private static String hostAndPort;
    private static String activeProfile;

    @Autowired
    public AppUtil(MessageSource messageSource) {
        AppUtil.messageSource = messageSource;
    }

    public static void flash(RedirectAttributes redirectAttributes, String kind, String messageKey) {

        redirectAttributes.addFlashAttribute("flashKind",kind);
        redirectAttributes.addFlashAttribute("flashMessage",AppUtil.getMessage(messageKey));
    }

    public static String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey,args, Locale.getDefault());
    }

    @Value("${hostAndPort}")
    public void setHostAndPort(String hostAndPort) {
        AppUtil.hostAndPort = hostAndPort;
    }

    public static boolean isDev() {
        return activeProfile.equals("dev");
    }
    public static String hostURL() {
        return (isDev() ? "http://" : "https://") + hostAndPort;
    }
}
