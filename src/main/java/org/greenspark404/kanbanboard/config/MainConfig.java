package org.greenspark404.kanbanboard.config;

import org.greenspark404.kanbanboard.data.TaskRepository;
import org.greenspark404.kanbanboard.data.UserRepository;
import org.greenspark404.kanbanboard.service.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MainConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public RegistrationService registrationService(MailService mailService,
                                                   UserService userService,
                                                   LocalizationService localizationService) {
        return new RegistrationServiceImpl(mailService, userService, localizationService);
    }

    @Bean
    public UserService userService(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Bean
    public TaskService taskService(TaskRepository taskRepository) {
        return new TaskServiceImpl(taskRepository);
    }

    @Bean
    public LocalizationService localizationService(MessageSource messageSource) {
        return new LocalizationServiceImpl(messageSource);
    }
}
