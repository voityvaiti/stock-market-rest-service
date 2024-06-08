package com.myproject.stockmarketrestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myproject.stockmarketrestservice.dto.request.ReportDetailsRequestDto;
import com.myproject.stockmarketrestservice.dto.response.ReportDetailsResponseDto;
import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import com.myproject.stockmarketrestservice.service.abstraction.ReportDetailsService;
import org.bson.types.ObjectId;
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

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReportDetailsControllerTest {

    @MockBean
    ReportDetailsService reportDetailsService;

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    MockMvc mockMvc;

    @Value("${api-prefix}")
    private String apiPrefix;

    private static final ObjectId reportDetailsId = new ObjectId();
    private static final UUID reportId = UUID.randomUUID();
    private static final ReportDetails reportDetails = new ReportDetails();
    private static final ReportDetailsRequestDto reportDetailsRequestDto = new ReportDetailsRequestDto();
    private static final ReportDetailsResponseDto reportDetailsResponseDto = new ReportDetailsResponseDto();
    private static Page<ReportDetails> reportDetailsPage;

    @BeforeAll
    static void init() {
        reportDetails.setId(reportDetailsId);
        reportDetails.setReportId(reportId);
        reportDetails.setFinancialData(new HashMap<>());

        reportDetailsRequestDto.setFinancialData(reportDetails.getFinancialData());
        reportDetailsRequestDto.setReportId(reportDetails.getReportId());

        reportDetailsResponseDto.setId(reportDetailsId.toString());
        reportDetailsResponseDto.setReportId(reportId);
        reportDetailsResponseDto.setFinancialData(reportDetails.getFinancialData());

        reportDetailsPage = new PageImpl<>(List.of(
                reportDetails, new ReportDetails(), new ReportDetails()
        ));
    }

    @Test
    void getAllPage_shouldReturnOkStatus() throws Exception {

        when(reportDetailsService.getAll(any(PageRequest.class))).thenReturn(reportDetailsPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/report-details"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllPage_shouldDoProperPageRequest() throws Exception {

        when(reportDetailsService.getAll(any(PageRequest.class))).thenReturn(reportDetailsPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/report-details")
                .param("page-number", "7")
                .param("page-size", "5"));

        verify(reportDetailsService).getAll(PageRequest.of(7, 5));
    }

    @Test
    void getById_shouldReturnOkStatus() throws Exception {

        when(reportDetailsService.getById(reportDetailsId)).thenReturn(reportDetails);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/report-details/" + reportDetailsId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getById_shouldReturnProperReportDetails() throws Exception {

        when(reportDetailsService.getById(reportDetailsId)).thenReturn(reportDetails);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/report-details/" + reportDetailsId))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ReportDetailsResponseDto actualResponseDto = objectMapper.readValue(responseBody, ReportDetailsResponseDto.class);
        assertEquals(reportDetailsResponseDto, actualResponseDto);
    }

    @Test
    void getDetailsById_shouldReturnOkStatus() throws Exception {

        when(reportDetailsService.getDetailsByReportId(reportId)).thenReturn(reportDetails);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/report-details/report/" + reportId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDetailsById_shouldReturnProperReportDetails() throws Exception {

        when(reportDetailsService.getDetailsByReportId(reportId)).thenReturn(reportDetails);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/report-details/report/" + reportId))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ReportDetailsResponseDto actualResponseDto = objectMapper.readValue(responseBody, ReportDetailsResponseDto.class);
        assertEquals(reportDetailsResponseDto, actualResponseDto);
    }

    @Test
    void create_shouldReturnCreatedStatus() throws Exception {

        when(reportDetailsService.create(any(ReportDetails.class))).thenReturn(reportDetails);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix + "/report-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportDetailsRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void create_shouldReturnProperReportDetails() throws Exception {

        when(reportDetailsService.create(any(ReportDetails.class))).thenReturn(reportDetails);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix + "/report-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportDetailsRequestDto)))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ReportDetailsResponseDto actualResponseDto = objectMapper.readValue(responseBody, ReportDetailsResponseDto.class);
        assertEquals(reportDetailsResponseDto, actualResponseDto);
    }

    @Test
    void delete_shouldReturnOkStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(apiPrefix + "/report-details/" + reportDetailsId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
