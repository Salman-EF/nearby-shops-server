/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.controllers;

import com.nearbyshops.configuration.CustomUserDetailsService;
import com.nearbyshops.models.AppUser;
import com.nearbyshops.security.JWTAuthenticationFilter;
import com.nearbyshops.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);
    @Autowired
    UserServices userServices;
    @Autowired
    protected AuthenticationManager authenticationManager;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        // Checking first if the email already exists
        if (userServices.findUserByEmail(user.getEmail()) != null) {
            logger.warn("Email already used!!");
            return ResponseEntity.badRequest().body("error-email");
        } else {
            // Register the user and userServices saveUser will take care of encrypting the password
            AppUser userSaved = userServices.saveUser(user);
            // Generate and return access_token automatically after successfully register
            if (userSaved!=null) {
                String token = JWTAuthenticationFilter.generateToken(userSaved.getEmail());
                return ResponseEntity.ok().body(token);
            }
            return ResponseEntity.badRequest().body("error-other");

        }
    }
}
