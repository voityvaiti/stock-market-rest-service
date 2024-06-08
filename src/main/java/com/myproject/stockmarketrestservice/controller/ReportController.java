package com.myproject.stockmarketrestservice.controller;

import com.myproject.stockmarketrestservice.dto.request.ReportRequestDto;
import com.myproject.stockmarketrestservice.dto.response.ReportResponseDto;
import com.myproject.stockmarketrestservice.mapper.ReportMapper;
import com.myproject.stockmarketrestservice.model.entity.Report;
import com.myproject.stockmarketrestservice.service.abstraction.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("${api-prefix}/reports")
@RequiredArgsConstructor
@Tag(name = "Report")
public class ReportController {

    private final ReportService reportService;

    private final ReportMapper reportMapper = Mappers.getMapper(ReportMapper.class);

    @Operation(summary = "Get reports page", description = "Returns reports page by selected page number and page size")
    @GetMapping
    public ResponseEntity<Page<ReportResponseDto>> getAllPage(@RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                              @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize) {

        Page<Report> reportPage = reportService.getAll(PageRequest.of(pageNumber, pageSize));

        return ResponseEntity.ok(reportPage.map(reportMapper::mapToResponseDto));
    }

    @Operation(summary = "Get reports page by company", description = "Returns reports page of selected company by selected page number and page size")
    @GetMapping("/company/{id}")
    public ResponseEntity<Page<ReportResponseDto>> getAllPageByCompany(@PathVariable("id") UUID companyId,
                                                                       @RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                                       @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize) {

        Page<Report> reportPage = reportService.getAllByCompanyId(companyId, PageRequest.of(pageNumber, pageSize));

        return ResponseEntity.ok(reportPage.map(reportMapper::mapToResponseDto));
    }

    @Operation(summary = "Get report by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDto> getById(@PathVariable UUID id) {

        Report report = reportService.getById(id);

        return ResponseEntity.ok(reportMapper.mapToResponseDto(report));
    }

    @Operation(summary = "Create report")
    @PostMapping
    public ResponseEntity<ReportResponseDto> create(@RequestBody @Valid ReportRequestDto reportRequestDto) {

        Report createdReport = reportService.create(
                reportMapper.mapToReport(reportRequestDto)
        );

        return new ResponseEntity<>(reportMapper.mapToResponseDto(createdReport), HttpStatus.CREATED);
    }

    @Operation(summary = "Update report", description = "Updates all fields of selected by ID report")
    @PutMapping("/{id}")
    public ResponseEntity<ReportResponseDto> update(@PathVariable UUID id, @RequestBody @Valid ReportRequestDto reportRequestDto) {

        Report updatedReport = reportService.update(
                id, reportMapper.mapToReport(reportRequestDto)
        );

        return ResponseEntity.ok(reportMapper.mapToResponseDto(updatedReport));
    }

    @Operation(summary = "Delete report by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        reportService.delete(id);

        return ResponseEntity.ok().build();
    }
}
