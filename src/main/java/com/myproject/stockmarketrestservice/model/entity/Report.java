package com.myproject.stockmarketrestservice.model.entity;

import javax.persistence.*;
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
    private LocalDateTime reportDate;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "net_profit")
    private BigDecimal netProfit;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
