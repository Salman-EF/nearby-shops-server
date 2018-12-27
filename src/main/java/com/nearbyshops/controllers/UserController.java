/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.controllers;

import com.nearbyshops.models.AppUser;
import com.nearbyshops.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserServices userServices;
    AuthenticationManager authenticationManager;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        // Checking first if the email already exists
        if (userServices.findUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email");
        } else {
            // Register the user and userServices saveUser will take care of encrypting the password
            AppUser userSaved = userServices.saveUser(user);
            return ResponseEntity.ok(userSaved);
        }
    }
}
