package com.myproject.stockmarketrestservice.dto.response;

import com.myproject.stockmarketrestservice.model.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {

    private UUID id;

    private LocalDateTime reportDate;

    private BigDecimal totalRevenue;

    private BigDecimal netProfit;

    private Company company;

}
