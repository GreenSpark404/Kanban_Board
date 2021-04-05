package org.greenspark404.kanbanboard.service;

import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.mvc.RegistrationFormData;
import org.greenspark404.kanbanboard.mvc.UserFormData;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username);

    User loadUserById(Long userId);

    User loadUserByEmail(String email);

    User createUser(RegistrationFormData registrationFormData);

    void updateUser(UserFormData userFormData, User user);

    void deleteUser(User user);

    Iterable<User> getAvailableAssignees(User user);

    Iterable<User> getAllUsers();

}
