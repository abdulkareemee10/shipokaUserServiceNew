package com.kenny.Authentication.system.securityConfig;

import com.kenny.Authentication.system.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                                .requestMatchers(HttpMethod.POST, "api/v1/auth/signup").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/otp/send").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/otp/validateOtp").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/usercategory/add").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/v1/usercategory/get").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/prof/save").permitAll()
                                .requestMatchers(HttpMethod.PUT, "api/v1/prof/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/business/post").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/v1/business/get").permitAll()
                                .requestMatchers(HttpMethod.PUT, "api/v1/business/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "api/v1/business/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/employee/post").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/v1/employee/get").permitAll()
                                .requestMatchers(HttpMethod.PUT, "api/v1/employee/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "api/v1/employee/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/businessDetails/post").permitAll()
                                .requestMatchers(HttpMethod.PUT, "api/v1/auth/{email}").permitAll()
                                .anyRequest().authenticated()
                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}