package org.greenspark404.kanbanboard.system;

import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.data.model.UserSettings;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ApplicationAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final LocaleResolver localeResolver;

    public ApplicationAuthenticationSuccessHandler(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);

        Optional.of(authentication.getPrincipal())
                .map(User.class::cast).map(User::getUserSettings).map(UserSettings::getPreferredLocale)
                .ifPresent(locale -> localeResolver.setLocale(request, response, locale));
    }
}
