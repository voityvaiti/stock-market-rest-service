package com.myproject.stockmarketrestservice.service.impl;

import com.myproject.stockmarketrestservice.exception.ResourceNotFoundException;
import com.myproject.stockmarketrestservice.model.entity.Company;
import com.myproject.stockmarketrestservice.model.entity.Report;
import com.myproject.stockmarketrestservice.repository.ReportRepository;
import com.myproject.stockmarketrestservice.service.abstraction.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ReportServiceImpl.class)
class ReportServiceImplTest {

    @MockBean
    private ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    private static final UUID reportId = UUID.randomUUID();
    private static final UUID companyId = UUID.randomUUID();
    private static final Report report = new Report();
    private static final Report newReport = new Report();
    private static final Page<Report> reportPage = new PageImpl<>(List.of(new Report(), new Report(), new Report()));

    @BeforeAll
    static void init() {

        report.setId(reportId);
        report.setCompany(new Company());

        Company company = new Company();
        company.setName("some name");

        newReport.setCompany(company);
    }

    @Test
    void getAll_shouldReturnPageOfReports() {

        Pageable pageable = PageRequest.of(0, 10);
        when(reportRepository.findAll(pageable)).thenReturn(reportPage);

        Page<Report> result = reportService.getAll(pageable);

        assertEquals(reportPage, result);
    }

    @Test
    void getById_shouldReturnReport_ifExists() {

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        Report result = reportService.getById(reportId);

        assertEquals(report, result);
    }

    @Test
    void getById_shouldThrowException_ifNotExists() {

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reportService.getById(reportId));
    }

    @Test
    void getAllByCompanyId_shouldReturnPageOfReports_ifExists() {

        Pageable pageable = PageRequest.of(0, 10);
        when(reportRepository.getAllByCompanyId(companyId, pageable)).thenReturn(reportPage);

        Page<Report> result = reportService.getAllByCompanyId(companyId, pageable);

        assertEquals(reportPage, result);
    }

    @Test
    void create_shouldSaveAndReturnReport() {

        when(reportRepository.save(newReport)).thenReturn(newReport);

        Report result = reportService.create(newReport);

        assertEquals(newReport, result);
        verify(reportRepository).save(newReport);
    }

    @Test
    void update_shouldSaveAndReturnUpdatedReport() {

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportRepository.save(report)).thenReturn(report);

        Report result = reportService.update(reportId, report);

        assertEquals(report, result);
        verify(reportRepository).save(report);
    }

    @Test
    void update_shouldThrowException_ifReportNotExists() {

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reportService.update(reportId, report));
    }

    @Test
    void delete_shouldRemoveReport_ifExists() {

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        reportService.delete(reportId);

        verify(reportRepository).delete(report);
    }

    @Test
    void delete_shouldThrowException_ifReportNotExists() {

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reportService.delete(reportId));
    }
}
