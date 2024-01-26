package com.kenny.Authentication.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {

 private String userId;
 private String email;
 private String password;
 private String confirmPassword;

}
