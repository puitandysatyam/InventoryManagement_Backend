package com.project.inventory.config;


import com.project.inventory.security.JwtRequestFilter;
import com.project.inventory.service.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * This is the main security configuration class.
 * It enables web security and configures JWT-based authentication
 * and role-based authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // This enables method-level security like @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private MongoUserDetailsService customUserDetailsService;

    /**
     * Defines the password encoder bean. We use BCrypt for hashing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the AuthenticationManager as a Bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the main security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Use the new CORS configuration source
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF protection as we are using JWT
                .csrf(csrf -> csrf.disable())
                // Configure authorization rules for different endpoints and roles
                .authorizeHttpRequests(auth -> auth
                        // Permit everyone to access the authentication endpoint
                        .requestMatchers("/api/auth/authenticate").permitAll()

                        // --- EXAMPLE RULES FOR INVENTORY ---
                        // Only ADMIN and EDITOR can create new items (POST)
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN", "EDITOR")
                        // Only ADMIN and EDITOR can update items (PUT)
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN", "EDITOR")
                        // Only ADMIN can delete items (DELETE)
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        // Any authenticated user (ADMIN, EDITOR, VIEWER) can view items (GET)
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()

                        // For any other request, the user must be authenticated
                        .anyRequest().authenticated()
                )
                // Configure session management to be stateless, as we are using JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Add our custom JWT filter to process the token on each request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) for the application.
     * This is the modern way to configure CORS with Spring Security 6+.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests from your React frontend
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        // Allow all standard methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow specific headers required for JWT auth
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // Allow credentials
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

