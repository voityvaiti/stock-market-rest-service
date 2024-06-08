package com.myproject.stockmarketrestservice.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDto {

    private LocalDateTime timestamp;

    private String message;

}
