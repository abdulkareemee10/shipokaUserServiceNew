package com.kenny.Authentication.system.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Builder
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private Role role;
    private Boolean isTermsAndCondition;

//    @CreationTimestamp
//    private LocalDateTime dateCreated;
//    @CreationTimestamp
//    private LocalDateTime modifiedAt;

}
