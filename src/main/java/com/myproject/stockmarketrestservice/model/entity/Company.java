package com.myproject.stockmarketrestservice.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot be larger that 100 characters")
    private String name;

    @Column(name = "registration_number", unique = true)
    @NotBlank(message = "Registration number cannot be blank")
    @Size(max = 100, message = "Registration number cannot be larger that 100 characters")
    private String registrationNumber;

    @Column(name = "address")
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 100, message = "Address cannot be larger that 100 characters")
    private String address;

    @Column(name = "created_at")
    @NotNull(message = "Created at datetime cannot be null")
    @PastOrPresent(message = "Created at date cannot be future")
    private LocalDateTime createdAt;

}
