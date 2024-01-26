package com.kenny.Authentication.system.controller;

import com.kenny.Authentication.system.Utils.JwtUtil;
import com.kenny.Authentication.system.dto.*;
import com.kenny.Authentication.system.service.ForgotPasswordService;
import com.kenny.Authentication.system.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(
            @RequestBody  Request request){
        return userService.signUp(request);
    }
    @PostMapping("/login")
    public LoginResponse userLogin(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw  new BadCredentialsException("incorrect username or password");
        }catch (DisabledException disabledException){
            httpServletResponse.sendError(HttpServletResponse.SC_FOUND, "user is not created, please register user first");
        return null;
        }
        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return new LoginResponse(jwt);
    }
//    @GetMapping("/login")
//    public String userLogins(String username){
//       userService.loadUserByUsername(username);
//       return "login successful, loading.........";
//    }


    @PutMapping("/{email}")
    public Response changePassword(@PathVariable("email") String email, @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
     return forgotPasswordService.changePassword(forgotPasswordRequest, email);

    }


}
