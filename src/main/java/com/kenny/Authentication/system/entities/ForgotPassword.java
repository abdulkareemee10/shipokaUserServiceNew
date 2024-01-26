package com.kenny.Authentication.system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "forgetPassword_tbl")

public class ForgotPassword {
    @Id
    private String id;
    private String emailAddress;
    private String otp;
    private LocalDateTime timeCreated;
    private LocalDateTime timeExpired;
    private Long userId;

}
