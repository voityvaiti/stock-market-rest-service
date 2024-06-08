package com.myproject.stockmarketrestservice.service.impl;

import com.myproject.stockmarketrestservice.exception.ResourceNotFoundException;
import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import com.myproject.stockmarketrestservice.repository.ReportDetailsRepository;
import com.myproject.stockmarketrestservice.service.abstraction.ReportDetailsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportDetailsServiceImpl implements ReportDetailsService {

    private final ReportDetailsRepository reportDetailsRepository;


    @Override
    public Page<ReportDetails> getAll(Pageable pageable) {
        return reportDetailsRepository.findAll(pageable);
    }

    @Override
    public ReportDetails getById(ObjectId id) {
        return getReportDetailsById(id);
    }

    @Override
    public ReportDetails getDetailsByReportId(UUID reportId) {
        return reportDetailsRepository.findByReportId(reportId).orElseThrow(
                () -> new ResourceNotFoundException("Not found report details for report with id: " + reportId)
        );
    }

    @Override
    public ReportDetails create(ReportDetails reportDetails) {
        return reportDetailsRepository.save(reportDetails);
    }

    @Override
    public void delete(ObjectId id) {

        ReportDetails reportDetailsToDelete = getReportDetailsById(id);

        reportDetailsRepository.delete(reportDetailsToDelete);
    }


    private ReportDetails getReportDetailsById(ObjectId id) {
        return reportDetailsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ReportDetails with id " + id + " not found")
        );
    }

}
