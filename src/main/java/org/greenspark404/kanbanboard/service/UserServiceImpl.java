package org.greenspark404.kanbanboard.service;

import org.greenspark404.kanbanboard.data.UserRepository;
import org.greenspark404.kanbanboard.data.model.Role;
import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.data.model.UserSettings;
import org.greenspark404.kanbanboard.mvc.RegistrationFormData;
import org.greenspark404.kanbanboard.mvc.UserFormData;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(RegistrationFormData registrationFormData) {
        User user = new User();
        user.getRoles().add(Role.USER);
        user.setLogin(registrationFormData.getLogin());
        user.setEmail(registrationFormData.getEmail());
        user.setPassword(passwordEncoder.encode(registrationFormData.getPassword()));

        userRepository.save(user);
        return user;
    }

    @Override
    public void updateUser(UserFormData userFormData, User user) {
        user.setFirstName(userFormData.getFirstName());
        user.setLastName(userFormData.getLastName());
        if (userFormData.getUserLocale() != null) {
            UserSettings userSettings = user.getUserSettings();
            if (userSettings == null) {
                userSettings = new UserSettings();
                user.setUserSettings(userSettings);
            }
            userSettings.setPreferredLocale(userFormData.getUserLocale());
        }
        if (StringUtils.hasLength(userFormData.getLogin())) {
            user.setLogin(userFormData.getLogin());
        }
        if (StringUtils.hasLength(userFormData.getPassword())) {
            user.setPassword(passwordEncoder.encode(userFormData.getPassword()));
        }
        if (StringUtils.hasLength(userFormData.getEmail())) {
            user.setEmail(userFormData.getEmail());
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    @Override
    public User loadUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Iterable<User> getAvailableAssignees(User user) {
        return userRepository.findAll();
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}
