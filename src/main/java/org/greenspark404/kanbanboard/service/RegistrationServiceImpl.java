package org.greenspark404.kanbanboard.service;

import org.greenspark404.kanbanboard.mvc.RegistrationFormData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final String MAIL_SUBJECT_KEY =
            "registration.mail.subject";

    private static final String MAIL_MESSAGE_KEY =
            "registration.mail.message";

    private final MailService mailService;

    private final UserService userService;

    private final LocalizationService localizationService;

    private final Cache<String, RegistrationFormData> cache;

    @Value("${hostname}")
    private String hostname;

    public RegistrationServiceImpl(MailService mailService, UserService userService, LocalizationService localizationService) {
        MutableConfiguration<String, RegistrationFormData> cacheConfig = new MutableConfiguration<>();
        cacheConfig.setStatisticsEnabled(true);
        cacheConfig.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_DAY));

        this.cache = Caching.getCachingProvider().getCacheManager()
                .createCache(RegistrationServiceImpl.class.getName(), cacheConfig);
        this.mailService = mailService;
        this.userService = userService;
        this.localizationService = localizationService;
    }

    @Override
    public boolean isEmailConfirmationNeeded() {
        return mailService.isMailServiceEnabled();
    }

    @Override
    public void sendUserToConfirmation(RegistrationFormData registrationFormData) {
        String confirmationCode = UUID.randomUUID().toString();
        String subject = localizationService.getLocalizedMessage(MAIL_SUBJECT_KEY);
        String message = localizationService.getLocalizedMessage(MAIL_MESSAGE_KEY,
                registrationFormData.getLogin(), String.format("http://%s/confirmUser/%s", hostname, confirmationCode));

        cache.put(confirmationCode, registrationFormData);
        mailService.send(registrationFormData.getEmail(), subject, message);
    }

    @Override
    public RegistrationFormData getUserFormDataByConfirmationCode(String confirmationCode) {
        RegistrationFormData registrationFormData = cache.get(confirmationCode);
        cache.remove(confirmationCode);

        return registrationFormData;
    }

    @Override
    public void registerUser(RegistrationFormData registrationFormData) {
        userService.createUser(registrationFormData);
    }

}
