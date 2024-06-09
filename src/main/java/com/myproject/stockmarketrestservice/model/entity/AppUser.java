package com.myproject.stockmarketrestservice.model.entity;

import com.myproject.stockmarketrestservice.model.enums.Role;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "usr")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 100, message = "Username cannot be larger that 100 characters")
    private String username;

    @Column(name = "password")
    @ToString.Exclude
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100, message = "Password cannot be larger that 100 characters")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role cannot be null")
    private Role role;

}
