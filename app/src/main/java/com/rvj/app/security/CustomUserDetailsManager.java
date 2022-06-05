package com.rvj.app.security;

import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsManager implements UserDetailsManager {

    @Autowired
    private UserRegistrationService userRegistrationService;

    public CustomUserDetailsManager(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public void createUser(UserDetails user) {
        userRegistrationService.createUser(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRegistrationService.isUserExist(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRegistrationService.loadUser(username);
        if(user.isPresent()) {
            User userObject = user.get();
            return new CustomUserDetails(userObject.getUserName(), userObject.getPassword(), userObject.getUserLevel());
        }
        return null;
    }
}
