package cl.luci.example.springboot.utils;

import cl.luci.example.springboot.dto.UserDetailsImpl;
import cl.luci.example.springboot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private static Thread sessionUser;

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

    public static User getSessionUser() {
        UserDetailsImpl auth = getAuth();
        return auth == null ? null : auth.getUser();
    }

    public static UserDetailsImpl getAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Object principal = auth.getPrincipal();

            if (principal instanceof UserDetailsImpl) {
                return (UserDetailsImpl) principal;
            }
        }

        return null;
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

    public static void validate(boolean valid, String msgContent,Object... args) {

        if (!valid) {
            throw new RuntimeException(getMessage(msgContent,args));
        }
    }

    public static String hostUrl() {
        return (isDev() ? "http://" : "https://") + hostAndPort;
    }

}
