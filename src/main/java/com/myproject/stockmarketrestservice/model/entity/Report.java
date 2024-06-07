package com.myproject.stockmarketrestservice.model.entity;

import com.myproject.stockmarketrestservice.model.entity.Company;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "report_date")
    private LocalDateTime reportDate;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "net_profit")
    private BigDecimal netProfit;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
