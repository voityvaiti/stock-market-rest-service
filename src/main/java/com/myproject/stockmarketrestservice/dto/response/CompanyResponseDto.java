package com.myproject.stockmarketrestservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {

    private UUID id;

    private String name;

    private String registrationNumber;

    private String address;

    private LocalDateTime createdAt;

}
