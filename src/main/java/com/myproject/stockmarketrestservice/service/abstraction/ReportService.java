package com.myproject.stockmarketrestservice.service.abstraction;

import com.myproject.stockmarketrestservice.model.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReportService {

    Page<Report> getAll(Pageable pageable);

    Report getById(UUID id);

    Page<Report> getAllByCompanyId(UUID companyId, Pageable pageable);

    Report create(Report report);

    Report update(UUID id, Report report);

    void delete(UUID id);

}
