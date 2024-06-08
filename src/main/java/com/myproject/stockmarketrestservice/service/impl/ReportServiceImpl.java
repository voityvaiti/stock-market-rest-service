package com.myproject.stockmarketrestservice.service.impl;

import com.myproject.stockmarketrestservice.exception.ResourceNotFoundException;
import com.myproject.stockmarketrestservice.mapper.ReportMapper;
import com.myproject.stockmarketrestservice.model.entity.Report;
import com.myproject.stockmarketrestservice.repository.ReportRepository;
import com.myproject.stockmarketrestservice.service.abstraction.ReportService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper = Mappers.getMapper(ReportMapper.class);


    @Override
    public Page<Report> getAll(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    @Override
    public Report getById(UUID id) {
        return getReportById(id);
    }

    @Override
    public Page<Report> getAllByCompanyId(UUID companyId, Pageable pageable) {
        return reportRepository.getAllByCompanyId(companyId, pageable);
    }

    @Override
    public Report create(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Report update(UUID id, Report updatedReport) {

        Report reportToUpdate = getReportById(id);

        return reportRepository.save(
                reportMapper.updateFields(updatedReport, reportToUpdate)
        );
    }

    @Override
    public void delete(UUID id) {

        Report reportToDelete = getReportById(id);

        reportRepository.delete(reportToDelete);
    }


    private Report getReportById(UUID id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Report with id " + id + " not found")
        );
    }

}
