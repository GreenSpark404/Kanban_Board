package org.greenspark404.kanbanboard.controller;

import org.greenspark404.kanbanboard.mvc.RegistrationFormData;
import org.greenspark404.kanbanboard.service.RegistrationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final Validator registrationFormValidator;

    private final RegistrationService registrationService;

    public RegistrationController(@Qualifier("userFormValidator") Validator registrationFormValidator,
                                  RegistrationService registrationService) {
        this.registrationFormValidator = registrationFormValidator;
        this.registrationService = registrationService;
    }

    @ModelAttribute("registrationForm")
    public RegistrationFormData getUserRegistrationForm() {
        return new RegistrationFormData();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(registrationFormValidator);
    }

    @GetMapping("/confirmUser/{confirmationCode}")
    public String confirmUser(@PathVariable String confirmationCode) {
        RegistrationFormData formData =
                registrationService.getUserFormDataByConfirmationCode(confirmationCode);
        if (formData == null) {
            return "redirect:/login?confirmFailed";
        }

        registrationService.registerUser(formData);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("registrationForm") RegistrationFormData registrationForm,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (registrationService.isEmailConfirmationNeeded()) {
            registrationService.sendUserToConfirmation(registrationForm);
            return "redirect:/login?confirm";
        }

        registrationService.registerUser(registrationForm);
        return "redirect:/login";
    }
}
