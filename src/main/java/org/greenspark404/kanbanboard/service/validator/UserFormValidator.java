package org.greenspark404.kanbanboard.service.validator;

import org.greenspark404.kanbanboard.mvc.UserForm;
import org.greenspark404.kanbanboard.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service("userFormValidator")
public class UserFormValidator implements Validator {

    private final UserService userService;

    public UserFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm formData = (UserForm) target;

        if (!ObjectUtils.nullSafeEquals(formData.getPassword(), formData.getPasswordConfirm())) {
            errors.rejectValue("password", "common.validation.passwordsDoesNotMatch");
            errors.rejectValue("passwordConfirm", "common.validation.passwordsDoesNotMatch");
        }
        if (userService.loadUserByUsername(formData.getLogin()) != null) {
            errors.rejectValue("login", "common.validation.alreadyRegistered",
                    new String[]{"login"}, null);
        }
        if (userService.loadUserByEmail(formData.getEmail()) != null) {
            errors.rejectValue("email", "common.validation.alreadyRegistered",
                    new String[]{"email"}, null);
        }
    }
}
