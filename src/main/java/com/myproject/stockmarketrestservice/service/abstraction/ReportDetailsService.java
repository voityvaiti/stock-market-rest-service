package com.myproject.stockmarketrestservice.service.abstraction;

import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReportDetailsService {

    Page<ReportDetails> getAll(Pageable pageable);

    ReportDetails getById(ObjectId id);

    ReportDetails getDetailsByReportId(UUID reportId);

    ReportDetails create(ReportDetails reportDetails);

    void delete(ObjectId id);

}
