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
import org.springframework.security.core.context.SecurityContextHolder;
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
        AppUser user = userRepository.findByEmail(email);
        return user;
    }
    public AppUser findUserByEmailAndPass(AppUser user) {
        AppUser userFound = userRepository.findByEmail(user.getEmail());
        if (userFound!=null && bCryptPasswordEncoder.matches(user.getPassword(),userFound.getPassword())) {
            return userFound;
        }
        return null;
    }

    public AppUser authenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = findUserByEmail(email);
        return user;
    }

    public AppUser saveUser(AppUser user) {
        // Registering new user:
        // Encrypting the password specified before send to database and set user as enabled
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role role = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        userRepository.save(user);
        logger.info("Save "+ user.toString());

        return user;
    }

    public AppUser updateUser(AppUser user) {
        try {
            userRepository.findById(user.getId());
            userRepository.save(user);
            logger.info("Update "+ user.toString());
        } catch(NoSuchElementException ex) {
            // AppUser not exists
            logger.warn("AppUser: "+ user.getId()+", not found");
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
            // AppUser not exists
            logger.warn("AppUser: "+userId+", not found");
        }
        return deletingStatus;
    }

    public AppUser updatePassword(String oldPass, String newPass) {
        AppUser user = null;
        try {
            AppUser userToUpdate = authenticatedUser();
            if (userToUpdate.getPassword().equals(bCryptPasswordEncoder.encode(oldPass))) {
                userToUpdate.setPassword(bCryptPasswordEncoder.encode(newPass));
                userRepository.save(userToUpdate);
                user = userToUpdate;
                logger.info("Update Password for: "+ userToUpdate.toString());
            }
        } catch(NoSuchElementException ex) {
            // AppUser not exists
            logger.warn("AppUser: "+ user.getEmail()+", not found");
        }
        return user;
    }
}
