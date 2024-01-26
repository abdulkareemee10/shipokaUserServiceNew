package com.kenny.Authentication.system.service.impl;

import com.kenny.Authentication.system.dto.*;
import com.kenny.Authentication.system.entities.User;
import com.kenny.Authentication.system.repository.UserRepository;
import com.kenny.Authentication.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<Response> signUp(Request request) {
        //if the user exists - return error
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .responseMessage("this account is  already in use")
                    .build());
        }
        //if password doesn't match

        if (request.getPassword().equals(request.getConfirmPassword())) {
            final String ids = UUID.randomUUID().toString().replace("-", "");
                  User user = User.builder()
                    .id(ids)
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .isTermsAndCondition(request.getIsTermsAndCondition())
                    .build();
            if (request.getIsTermsAndCondition()) {
                User savedUser = userRepository.save(user);
                return ResponseEntity.ok(Response.builder().build());

            }else {
                return ResponseEntity.ok(Response.builder().responseMessage("agree with terms and conditions").build());
            }
        }
        return ResponseEntity.ok(Response.builder()
                .responseMessage("password does not match").build());
    }


    public String deleteUserById (String  id){

        userRepository.deleteById(id);
        return "user deleted successfully";

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        if (!user.isPresent()){
            throw new UsernameNotFoundException("user with this email is not found");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
    }

//    public Collection<? extends GrantedAuthority> getAuthorities(){
//        User user = new User();
//        return  List.of(new SimpleGrantedAuthority(user.getRole().name()));
//    }






    @Override
    public ResponseEntity<Response> login(LoginRequest request) {
        return null;
    }

    @Override
    public Response sendOtp() {
        return null;
    }

    @Override
    public Response validateOtp() {
        return null;
    }

    @Override
    public Response resetPassword() {
        return null;
    }




}
