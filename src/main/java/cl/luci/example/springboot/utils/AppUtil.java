package cl.luci.example.springboot.utils;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AppUtil(MessageSource messageSource) {
        AppUtil.messageSource = messageSource;
    }

    public static void flash(RedirectAttributes redirectAttributes, String kind, String messageKey) {

        redirectAttributes.addFlashAttribute("flashKind",kind);
        redirectAttributes.addFlashAttribute("flashMessage",AppUtil.getMessage(messageKey));
    }

    private static String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey,args, Locale.getDefault());
    }
}
