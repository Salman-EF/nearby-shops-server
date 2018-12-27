/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.services;

import com.nearbyshops.models.AppUser;
import com.nearbyshops.models.Role;
import com.nearbyshops.repositories.RoleRepository;
import com.nearbyshops.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

@Service
public class UserServices {

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUser findUserByEmail(String email) {
        AppUser appUser = userRepository.findByEmail(email);
        if (appUser != null) logger.info("Email exists: "+email);
        return appUser;
    }

    public AppUser saveUser(AppUser appUser) {
        // Registering new appUser:
        // Encrypting the password specified before send to database and set appUser as enabled
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setEnabled(true);
        Role role = roleRepository.findByRole("USER");
        appUser.setRoles(new HashSet<>(Arrays.asList(role)));
        userRepository.save(appUser);
        logger.info("Save "+ appUser.toString());

        return appUser;
    }

    public AppUser updateUser(AppUser appUser) {
        try {
            userRepository.findById(appUser.getId());
            userRepository.save(appUser);
            logger.info("Update "+ appUser.toString());
        } catch(NoSuchElementException ex) {
            // AppUser not exists
            logger.warn("AppUser: "+ appUser.getId()+", not found");
            appUser = null;
        }

        return appUser;
    }

    public boolean deleteUser(String userId) {
        boolean deletingStatus = false;
        logger.info("Delete "+userId);
        try {
            userRepository.findById(userId);
            userRepository.deleteById(userId);
            deletingStatus = true;
        } catch(NoSuchElementException ex) {
            // AppUser not exists
            logger.warn("AppUser: "+userId+", not found");
        }
        return deletingStatus;
    }
}
