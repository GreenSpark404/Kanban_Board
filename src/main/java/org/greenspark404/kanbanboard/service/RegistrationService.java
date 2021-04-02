package org.greenspark404.kanbanboard.service;

import org.greenspark404.kanbanboard.mvc.RegistrationFormData;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    void sendUserToConfirmation(RegistrationFormData registrationFormData);

    boolean isEmailConfirmationNeeded();

    void registerUser(RegistrationFormData registrationFormData);

    RegistrationFormData getUserFormDataByConfirmationCode(String confirmationCode);

}
