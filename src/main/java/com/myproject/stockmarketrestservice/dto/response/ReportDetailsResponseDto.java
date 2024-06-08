package com.myproject.stockmarketrestservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailsResponseDto {

    private String id;

    private UUID reportId;

    private Map<String, Object> financialData;

    private String comments;

}
