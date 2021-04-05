package org.greenspark404.kanbanboard.mvc;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class UserFormData implements UserForm {

    @Size(max = 32)
    private String login;

    @Size(max = 32)
    private String firstName;

    @Size(max = 32)
    private String lastName;

    @Size(min = 6, max = 32)
    private String password;

    @Size(min = 6, max = 32)
    private String passwordConfirm;

    @Email
    private String email;

    private String emailPlaceholder;

    private String loginPlaceholder;

    private final Set<Locale> availableLocales = new HashSet<>();

    private Locale userLocale;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPlaceholder() {
        return emailPlaceholder;
    }

    public void setEmailPlaceholder(String emailPlaceholder) {
        this.emailPlaceholder = emailPlaceholder;
    }

    public Locale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

    public String getLoginPlaceholder() {
        return loginPlaceholder;
    }

    public void setLoginPlaceholder(String loginPlaceholder) {
        this.loginPlaceholder = loginPlaceholder;
    }

    public Set<Locale> getAvailableLocales() {
        return availableLocales;
    }
}
