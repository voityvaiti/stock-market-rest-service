package com.myproject.stockmarketrestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myproject.stockmarketrestservice.dto.request.ReportRequestDto;
import com.myproject.stockmarketrestservice.dto.response.ReportResponseDto;
import com.myproject.stockmarketrestservice.model.entity.Report;
import com.myproject.stockmarketrestservice.service.abstraction.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReportControllerTest {

    @MockBean
    ReportService reportService;

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    MockMvc mockMvc;

    @Value("${api-prefix}")
    private String apiPrefix;

    private static final UUID reportId = UUID.randomUUID();
    private static final UUID companyId = UUID.randomUUID();
    private static final Report report = new Report();
    private static final ReportRequestDto reportRequestDto = new ReportRequestDto();
    private static final ReportResponseDto reportResponseDto = new ReportResponseDto();
    private static Page<Report> reportPage;

    @BeforeAll
    static void init() {
        report.setId(reportId);
        report.setTotalRevenue(BigDecimal.valueOf(644));

        reportRequestDto.setTotalRevenue(report.getTotalRevenue());
        reportRequestDto.setCompanyId(companyId);
        reportRequestDto.setNetProfit(BigDecimal.valueOf(123123));
        reportRequestDto.setReportDate(LocalDateTime.now().minusMinutes(10));

        reportResponseDto.setId(reportId);
        reportResponseDto.setTotalRevenue(BigDecimal.valueOf(644));

        reportPage = new PageImpl<>(List.of(
                report, new Report(), new Report()
        ));
    }

    @Test
    void getAllPage_shouldReturnOkStatus() throws Exception {

        when(reportService.getAll(any(PageRequest.class))).thenReturn(reportPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/reports"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllPage_shouldDoProperPageRequest() throws Exception {

        when(reportService.getAll(any(PageRequest.class))).thenReturn(reportPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/reports")
                .param("page-number", "2")
                .param("page-size", "1"));

        verify(reportService).getAll(PageRequest.of(2, 1));
    }

    @Test
    void getAllPageByCompany_shouldReturnOkStatus() throws Exception {

        when(reportService.getAllByCompanyId(any(UUID.class), any(PageRequest.class))).thenReturn(reportPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/reports/company/" + companyId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllPageByCompany_shouldDoProperPageRequest() throws Exception {

        when(reportService.getAllByCompanyId(any(UUID.class), any(PageRequest.class))).thenReturn(reportPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/reports/company/" + companyId)
                .param("page-number", "2")
                .param("page-size", "1"));

        verify(reportService).getAllByCompanyId(companyId, PageRequest.of(2, 1));
    }

    @Test
    void getById_shouldReturnOkStatus() throws Exception {

        when(reportService.getById(reportId)).thenReturn(report);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/reports/" + reportId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getById_shouldReturnProperReport() throws Exception {

        when(reportService.getById(reportId)).thenReturn(report);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/reports/" + reportId))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ReportResponseDto actualResponseDto = objectMapper.readValue(responseBody, ReportResponseDto.class);
        assertEquals(reportResponseDto, actualResponseDto);
    }

    @Test
    void create_shouldReturnCreatedStatus() throws Exception {

        when(reportService.create(any(Report.class))).thenReturn(report);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix + "/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void create_shouldReturnProperReport() throws Exception {

        when(reportService.create(any(Report.class))).thenReturn(report);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix + "/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportRequestDto)))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ReportResponseDto actualResponseDto = objectMapper.readValue(responseBody, ReportResponseDto.class);
        assertEquals(reportResponseDto, actualResponseDto);
    }

    @Test
    void update_shouldReturnOkStatus() throws Exception {

        when(reportService.update(any(UUID.class), any(Report.class))).thenReturn(report);

        mockMvc.perform(MockMvcRequestBuilders.put(apiPrefix + "/reports/" + reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update_shouldReturnProperReport() throws Exception {

        when(reportService.update(any(UUID.class), any(Report.class))).thenReturn(report);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(apiPrefix + "/reports/" + reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportRequestDto)))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ReportResponseDto actualResponseDto = objectMapper.readValue(responseBody, ReportResponseDto.class);
        assertEquals(reportResponseDto, actualResponseDto);
    }

    @Test
    void delete_shouldReturnOkStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(apiPrefix + "/reports/" + reportId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
