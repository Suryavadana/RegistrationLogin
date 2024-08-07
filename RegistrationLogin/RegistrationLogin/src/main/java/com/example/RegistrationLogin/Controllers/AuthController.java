package com.example.RegistrationLogin.Controllers;


import com.example.RegistrationLogin.Models.DTO.LoginFormDTO;
import com.example.RegistrationLogin.Models.DTO.RegistrationFormDTO;
import com.example.RegistrationLogin.Models.User;
import com.example.RegistrationLogin.Repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty( )) {
            return null;
        }

        return user.get( );
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId( ));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Map> processRegistrationForm(@RequestBody RegistrationFormDTO registrationFormDTO,
                                                       HttpServletRequest request) {
        ResponseEntity response = null;
        Map<String, String> responseBody = new HashMap<>( );
        try {
            User existingUser = userRepository.findByUsername(registrationFormDTO.getUsername( ));
            if (existingUser == null && !registrationFormDTO.getUsername( ).isEmpty( ) && !registrationFormDTO.getPassword( ).isEmpty( )) {
                responseBody.put("message", "Given user details are successfully registered");
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(responseBody);
                User newUser = new User(registrationFormDTO.getUsername( ),
                        registrationFormDTO.getPassword( ),
                        registrationFormDTO.getFirstName( ),
                        registrationFormDTO.getLastName( ),
                        registrationFormDTO.getEmail( ),
                        registrationFormDTO.getPhoneNumber( ),
                        registrationFormDTO.getAddress( ));
                setUserInSession(request.getSession( ), newUser);
                userRepository.save(newUser);
            } else if (existingUser != null) {
                responseBody.put("message", "User Already Exists.");
                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseBody);
            } else if (registrationFormDTO.getUsername( ).isEmpty( )) {
                responseBody.put("message", "Username required.");
                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseBody);
            } else if (registrationFormDTO.getPassword( ).isEmpty( )) {
                responseBody.put("message", "Password required");
                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseBody);
            }
        } catch (Exception ex) {
            responseBody.put("message", "An exception occurred due to " + ex.getMessage( ));
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseBody);
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Map> processLoginForm(@RequestBody LoginFormDTO loginFormDTO, HttpServletRequest request) {

        ResponseEntity response = null;
        Map<String, String> responseBody = new HashMap<>( );
        User theUser = userRepository.findByUsername(loginFormDTO.getUsername( ));
        String password = loginFormDTO.getPassword( );
        if (theUser == null) {
            responseBody.put("message", "Username does not exist");
            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseBody);
        } else if (!theUser.isMatchingPassword(password)) {
            responseBody.put("message", "Password does not match");
            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseBody);
        } else {
            setUserInSession(request.getSession( ), theUser);
            responseBody.put("message", "User successfully logged in.");
            responseBody.put("username", theUser.getUsername( ));
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(responseBody);
        }
        return response;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession( ).invalidate( );
        return "redirect:/login";
    }
}