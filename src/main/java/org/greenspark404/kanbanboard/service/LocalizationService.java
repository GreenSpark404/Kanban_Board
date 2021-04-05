package org.greenspark404.kanbanboard.service;

import java.util.List;
import java.util.Locale;

public interface LocalizationService {

    String getLocalizedMessage(String messageKey, Object... args);

    List<Locale> getAvailableLocales();
}
