package org.greenspark404.kanbanboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public interface LocalizationService {

    String getLocalizedMessage(String messageKey, Object... args);

    List<Locale> getAvailableLocales();
}
