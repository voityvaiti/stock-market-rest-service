package com.myproject.stockmarketrestservice.controller;

import com.myproject.stockmarketrestservice.dto.request.ReportDetailsRequestDto;
import com.myproject.stockmarketrestservice.dto.response.ReportDetailsResponseDto;
import com.myproject.stockmarketrestservice.mapper.ReportDetailsMapper;
import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import com.myproject.stockmarketrestservice.service.abstraction.ReportDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("${api-prefix}/report-details")
@RequiredArgsConstructor
@Tag(name = "Report details")
public class ReportDetailsController {

    private final ReportDetailsService reportDetailsService;

    private final ReportDetailsMapper reportDetailsMapper = Mappers.getMapper(ReportDetailsMapper.class);


    @Operation(summary = "Get reports details page", description = "Returns reports details page by selected page number and page size")
    @GetMapping
    public ResponseEntity<Page<ReportDetailsResponseDto>> getAllPage(@RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                               @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize) {

        Page<ReportDetails> reportDetailsPage = reportDetailsService.getAll(PageRequest.of(pageNumber, pageSize));

        return ResponseEntity.ok(reportDetailsPage.map(reportDetailsMapper::mapToResponseDto));
    }

    @Operation(summary = "Get report details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReportDetailsResponseDto> getById(@PathVariable ObjectId id) {

        ReportDetails reportDetails = reportDetailsService.getById(id);

        return ResponseEntity.ok(reportDetailsMapper.mapToResponseDto(reportDetails));
    }

    @Operation(summary = "Get report details by report ID")
    @GetMapping("/report/{id}")
    public ResponseEntity<ReportDetailsResponseDto> getDetailsById(@PathVariable UUID id) {

        ReportDetails reportDetails = reportDetailsService.getDetailsByReportId(id);

        return ResponseEntity.ok(reportDetailsMapper.mapToResponseDto(reportDetails));
    }

    @Operation(summary = "Create report details")
    @PostMapping
    public ResponseEntity<ReportDetailsResponseDto> create(@RequestBody @Valid ReportDetailsRequestDto reportDetailsRequestDto) {

        ReportDetails createdReportDetails = reportDetailsService.create(
                reportDetailsMapper.mapToReportDetails(reportDetailsRequestDto)
        );

        return new ResponseEntity<>(reportDetailsMapper.mapToResponseDto(createdReportDetails), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete report details by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ObjectId id) {

        reportDetailsService.delete(id);

        return ResponseEntity.ok().build();
    }

}
