package cl.luci.example.springboot.dto;

import cl.luci.example.springboot.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Oreste Luci
 */
public class SignupForm {

    @NotNull
    @Size(min = 1,max = 255)
    @Pattern(regexp= User.EMAIL_PATTERN, message="{emailPatternError}")
    private String email;

    @NotNull
    @Size(min = 1,max = 100, message="{nameSizeError}")
    private String name;

    @NotNull
    @Size(min = 6,max = 30)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupForm{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
