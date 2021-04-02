package org.greenspark404.kanbanboard.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;

    private final List<Locale> availableLocales =
            Arrays.asList(new Locale("ru"), Locale.ENGLISH);

    public LocalizationServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getLocalizedMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, args, locale);
    }

    @Override
    public List<Locale> getAvailableLocales() {
        return Collections.unmodifiableList(availableLocales);
    }

}
