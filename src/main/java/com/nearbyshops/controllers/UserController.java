/**
 * Build By SALMAN EL FADILI
 **/
package com.nearbyshops.controllers;

import com.nearbyshops.models.AppUser;
import com.nearbyshops.security.JWTAuthenticationFilter;
import com.nearbyshops.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);
    @Autowired
    UserServices userServices;

    // @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {
        AppUser userFound = userServices.findUserByEmailAndPass(user);
        System.out.println("User: "+userFound.toString());
        if (userFound != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Authentication authStatus = SecurityContextHolder.getContext().getAuthentication();

            return ResponseEntity.ok(JWTAuthenticationFilter.generateToken(authStatus.getName()));
        }
        return null;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        // Checking first if the email already exists
        if (userServices.findUserByEmail(user.getEmail()) != null) {
            logger.warn("Email already used!!");
            return ResponseEntity.ok("error-email");
        } else {
            // Register the user and userServices saveUser will take care of encrypting the password
            AppUser userSaved = userServices.saveUser(user);
            // Generate and return access_token automatically after successfully register
            if (userSaved!=null) {
                String jwt = JWTAuthenticationFilter.generateToken(userSaved.getEmail());
                return ResponseEntity.ok().body(jwt);
            }
            return ResponseEntity.ok("error-other");
        }
    }

    @GetMapping("/api/users/me")
    public String authenticatedUser() {
        return userServices.authenticatedUser().getEmail();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response){
        HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        session= request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("logout");
    }
}
