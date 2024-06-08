package com.myproject.stockmarketrestservice.service.impl;

import com.myproject.stockmarketrestservice.exception.ResourceNotFoundException;
import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import com.myproject.stockmarketrestservice.repository.ReportDetailsRepository;
import com.myproject.stockmarketrestservice.service.abstraction.ReportDetailsService;
import org.bson.types.ObjectId;
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

@SpringBootTest(classes = ReportDetailsServiceImpl.class)
class ReportDetailsServiceImplTest {

    @MockBean
    private ReportDetailsRepository reportDetailsRepository;

    @Autowired
    private ReportDetailsService reportDetailsService;

    private static final ObjectId reportDetailsId = new ObjectId();
    private static final UUID reportId = UUID.randomUUID();
    private static final ReportDetails reportDetails = new ReportDetails();
    private static final ReportDetails newReportDetails = new ReportDetails();
    private static final Page<ReportDetails> reportDetailsPage = new PageImpl<>(List.of(new ReportDetails(), new ReportDetails(), new ReportDetails()));

    @BeforeAll
    static void init() {

        reportDetails.setId(reportDetailsId);
        reportDetails.setReportId(reportId);

        newReportDetails.setReportId(UUID.randomUUID());
    }

    @Test
    void getAll_shouldReturnPageOfReportDetails() {

        Pageable pageable = PageRequest.of(0, 10);
        when(reportDetailsRepository.findAll(pageable)).thenReturn(reportDetailsPage);

        Page<ReportDetails> result = reportDetailsService.getAll(pageable);

        assertEquals(reportDetailsPage, result);
    }

    @Test
    void getById_shouldReturnReportDetails_ifExists() {

        when(reportDetailsRepository.findById(reportDetailsId)).thenReturn(Optional.of(reportDetails));

        ReportDetails result = reportDetailsService.getById(reportDetailsId);

        assertEquals(reportDetails, result);
    }

    @Test
    void getById_shouldThrowException_ifNotExists() {

        when(reportDetailsRepository.findById(reportDetailsId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reportDetailsService.getById(reportDetailsId));
    }

    @Test
    void getDetailsByReportId_shouldReturnReportDetails_ifExists() {

        when(reportDetailsRepository.findByReportId(reportId)).thenReturn(Optional.of(reportDetails));

        ReportDetails result = reportDetailsService.getDetailsByReportId(reportId);

        assertEquals(reportDetails, result);
    }

    @Test
    void getDetailsByReportId_shouldThrowException_ifNotExists() {

        when(reportDetailsRepository.findByReportId(reportId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reportDetailsService.getDetailsByReportId(reportId));
    }

    @Test
    void create_shouldSaveAndReturnReportDetails() {

        when(reportDetailsRepository.save(newReportDetails)).thenReturn(newReportDetails);

        ReportDetails result = reportDetailsService.create(newReportDetails);

        assertEquals(newReportDetails, result);
        verify(reportDetailsRepository).save(newReportDetails);
    }

    @Test
    void delete_shouldRemoveReportDetails_ifExists() {

        when(reportDetailsRepository.findById(reportDetailsId)).thenReturn(Optional.of(reportDetails));

        reportDetailsService.delete(reportDetailsId);

        verify(reportDetailsRepository).delete(reportDetails);
    }

    @Test
    void delete_shouldThrowException_ifNotExists() {

        when(reportDetailsRepository.findById(reportDetailsId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reportDetailsService.delete(reportDetailsId));
    }
}
