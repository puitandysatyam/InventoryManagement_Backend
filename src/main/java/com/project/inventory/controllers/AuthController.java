package com.project.inventory.controllers;

import com.project.inventory.entity.AuthenticationRequest;
import com.project.inventory.entity.AuthenticationResponse;
import com.project.inventory.entity.RegisterRequest;
import com.project.inventory.entity.Users;
import com.project.inventory.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // CORRECTED: Use .username() and .password() accessor methods for the record
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        // CORRECTED: Use the .username() accessor method here as well
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(registerRequest.username()));
        if (mongoTemplate.exists(query, "users")) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        Users newUser = new Users();
        newUser.setName(registerRequest.username());
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        newUser.setRole(registerRequest.role() != null ? registerRequest.role() : "viewer");

        mongoTemplate.save(newUser, "users");

        return ResponseEntity.ok("User registered successfully!");
    }
}