package com.myproject.stockmarketrestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDto {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot be larger that 100 characters")
    private String name;

    @NotBlank(message = "Registration number cannot be blank")
    @Size(max = 100, message = "Registration number cannot be larger that 100 characters")
    private String registrationNumber;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 100, message = "Address cannot be larger that 100 characters")
    private String address;

    @NotNull(message = "Created at datetime cannot be null")
    @PastOrPresent(message = "Created at date cannot be future")
    private LocalDateTime createdAt;

}
