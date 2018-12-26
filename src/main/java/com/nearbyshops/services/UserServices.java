/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.services;

import com.nearbyshops.models.Role;
import com.nearbyshops.models.User;
import com.nearbyshops.repositories.RoleRepository;
import com.nearbyshops.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class UserServices {

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role role = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        userRepository.save(user);
        logger.info("Save "+user.toString());

        return user;
    }

    public User updateUser(User user) {
        try {
            userRepository.findById(user.getId());
            userRepository.save(user);
            logger.info("Update "+user.toString());
        } catch(NoSuchElementException ex) {
            // User not exists
            logger.warn("User: "+user.getId()+", not found");
            user = null;
        }

        return user;
    }

    public boolean deleteUser(String userId) {
        boolean deletingStatus = false;
        logger.info("Delete "+userId);
        try {
            userRepository.findById(userId);
            userRepository.deleteById(userId);
            deletingStatus = true;
        } catch(NoSuchElementException ex) {
            // User not exists
            logger.warn("User: "+userId+", not found");
        }
        return deletingStatus;
    }
}
