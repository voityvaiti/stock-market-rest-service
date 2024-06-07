package com.myproject.stockmarketrestservice.repository;

import com.myproject.stockmarketrestservice.model.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    Page<Report> getAllByCompanyId(UUID companyId, Pageable pageable);

}
