package org.greenspark404.kanbanboard.controller;

import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.mvc.UserFormData;
import org.greenspark404.kanbanboard.service.LocalizationService;
import org.greenspark404.kanbanboard.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
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
        UserFormData userFormData = new UserFormData();
        userFormData.getAvailableLocales().addAll(
                localizationService.getAvailableLocales());
        return userFormData;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(userFormValidator);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/usersList")
    public String getUsersList(@AuthenticationPrincipal User currentUser, Model model) {
        Iterable<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "usersList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editUser/{id}")
    public String prepareUserForm(@ModelAttribute("userForm") UserFormData userFormData,
                                  @PathVariable("id") Long userId, Model model) {
        User user = userService.loadUserById(userId);
        prepareUserForm(userFormData, user);
        model.addAttribute("userLogin", user.getLogin());
        return "userEditor";
    }

    @GetMapping("/editUser")
    public String editCurrentUser(@ModelAttribute("userForm") UserFormData userFormData,
                                  @AuthenticationPrincipal User currentUser, Model model) {
        prepareUserForm(userFormData, currentUser);
        model.addAttribute("userLogin", currentUser.getLogin());
        return "userEditor";
    }

    @PostMapping("/editUser")
    public String prepareUserForm(@Valid @ModelAttribute("userForm") UserFormData userFormData, BindingResult bindingResult,
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

        return String.format("redirect:/editUser/%d?success", user.getId());
    }

    private void prepareUserForm(UserFormData userFormData, User userToEdit) {
        userFormData.setFirstName(userToEdit.getFirstName());
        userFormData.setLastName(userToEdit.getLastName());
        userFormData.setLoginPlaceholder(userToEdit.getLogin());
        userFormData.setEmailPlaceholder(userToEdit.getEmail());

        if (userToEdit.getUserSettings() != null) {
            userFormData.setUserLocale(userToEdit.getUserSettings().getPreferredLocale());
        }
    }
}
