package com.myproject.stockmarketrestservice.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "report_date")
    @NotNull(message = "Report date cannot be null")
    @PastOrPresent(message = "Report date cannot be future")
    private LocalDateTime reportDate;

    @Column(name = "total_revenue")
    @NotNull(message = "Total revenue cannot be null")
    @Min(value = 0, message = "Total revenue must be positive or zero")
    private BigDecimal totalRevenue;

    @Column(name = "net_profit")
    @NotNull(message = "Net profit cannot be null")
    @Min(value = 0, message = "Net profit must be positive or zero")
    private BigDecimal netProfit;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull(message = "Company cannot be null")
    private Company company;

}
