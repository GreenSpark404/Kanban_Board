package org.greenspark404.kanbanboard.system;

import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.data.model.UserSettings;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationSuccessHandlerWrapper implements AuthenticationSuccessHandler {

    private final LocaleResolver localeResolver;

    private AuthenticationSuccessHandler successHandler =
            new SavedRequestAwareAuthenticationSuccessHandler();

    public AuthenticationSuccessHandlerWrapper(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        successHandler.onAuthenticationSuccess(request, response, authentication);

        Optional.of(authentication.getPrincipal())
                .map(User.class::cast).map(User::getUserSettings).map(UserSettings::getPreferredLocale)
                .ifPresent(locale -> localeResolver.setLocale(request, response, locale));
    }
}
