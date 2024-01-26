package com.kenny.Authentication.system.repository;

import com.kenny.Authentication.system.entities.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,String> {
}
