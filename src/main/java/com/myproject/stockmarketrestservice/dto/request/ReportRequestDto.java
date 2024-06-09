package com.myproject.stockmarketrestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {

    @NotNull(message = "Report date cannot be null")
    @PastOrPresent(message = "Report date cannot be future")
    private LocalDateTime reportDate;

    @NotNull(message = "Total revenue cannot be null")
    @Min(value = 0, message = "Total revenue must be positive or zero")
    private BigDecimal totalRevenue;

    @NotNull(message = "Net profit cannot be null")
    @Min(value = 0, message = "Net profit must be positive or zero")
    private BigDecimal netProfit;

    @NotNull(message = "Company ID cannot be null")
    private UUID companyId;

}
