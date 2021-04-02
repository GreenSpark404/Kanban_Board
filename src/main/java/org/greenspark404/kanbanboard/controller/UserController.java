package org.greenspark404.kanbanboard.controller;

import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.mvc.UserFormData;
import org.greenspark404.kanbanboard.service.LocalizationService;
import org.greenspark404.kanbanboard.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private final Validator userFormValidator;

    private final UserService userService;

    private final LocalizationService localizationService;

    public UserController(@Qualifier("userFormValidator") Validator userFormValidator,
                          UserService userService, LocalizationService localizationService) {
        this.userFormValidator = userFormValidator;
        this.userService = userService;
        this.localizationService = localizationService;
    }

    @ModelAttribute("userForm")
    public UserFormData userFormData() {
        return new UserFormData();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(userFormValidator);
    }

    @GetMapping("/editUser")
    public String editUser(@ModelAttribute("userForm") UserFormData userFormData,
                           @AuthenticationPrincipal User currentUser, Model model) {
        userFormData.setFirstName(currentUser.getFirstName());
        userFormData.setLastName(currentUser.getLastName());
        userFormData.setEmailPlaceholder(currentUser.getEmail());

        if (currentUser.getUserSettings() != null) {
            userFormData.setUserLocale(currentUser.getUserSettings().getPreferredLocale());
        }

        model.addAttribute("userLogin", currentUser.getLogin());
        model.addAttribute("availableLocales", localizationService.getAvailableLocales());
        return "userEditor";
    }

    @PostMapping("/editUser")
    public String editUser(@Valid @ModelAttribute("userForm") UserFormData userFormData, BindingResult bindingResult,
                           @AuthenticationPrincipal User currentUser,
                           @RequestParam("userLogin") String userLogin, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userLogin", userLogin);
            return "userEditor";
        }

        User user = userService.loadUserByUsername(userLogin);
        userService.updateUser(userFormData, user);

        if (user.equals(currentUser)) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }

        return "redirect:/editUser?success";
    }
}
