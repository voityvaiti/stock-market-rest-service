package com.myproject.stockmarketrestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailsRequestDto {

    @NotNull(message = "Report ID cannot be null")
    private UUID reportId;

    private Map<String, Object> financialData;

    private String comments;

}
