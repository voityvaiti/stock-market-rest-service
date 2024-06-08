package com.myproject.stockmarketrestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myproject.stockmarketrestservice.dto.request.CompanyRequestDto;
import com.myproject.stockmarketrestservice.dto.response.CompanyResponseDto;
import com.myproject.stockmarketrestservice.model.entity.Company;
import com.myproject.stockmarketrestservice.service.abstraction.CompanyService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CompanyControllerTest {

    @MockBean
    CompanyService companyService;

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    MockMvc mockMvc;

    @Value("${api-prefix}")
    private String apiPrefix;

    private static final UUID companyId = UUID.randomUUID();
    private static final Company company = new Company();
    private static final CompanyRequestDto companyRequestDto = new CompanyRequestDto();
    private static final CompanyResponseDto companyResponseDto = new CompanyResponseDto();
    private static Page<Company> companyPage;

    @BeforeAll
    static void init() {
        company.setId(companyId);
        company.setName("Test Company");

        companyRequestDto.setName(company.getName());
        companyRequestDto.setAddress("some address");
        companyRequestDto.setCreatedAt(LocalDateTime.now());
        companyRequestDto.setRegistrationNumber("s123j123b3hg1v23");

        companyResponseDto.setId(companyId);
        companyResponseDto.setName("Test Company");

        companyPage = new PageImpl<>(List.of(
                company, new Company(), new Company()
        ));
    }

    @Test
    void getAllPage_shouldReturnOkStatus() throws Exception {

        when(companyService.getAll(any(PageRequest.class))).thenReturn(companyPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllPage_shouldDoProperPageRequest() throws Exception {

        when(companyService.getAll(any(PageRequest.class))).thenReturn(companyPage);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/companies")
                        .param("page-number", "4")
                        .param("page-size", "12"));

        verify(companyService).getAll(PageRequest.of(4, 12));
    }

    @Test
    void getById_shouldReturnOkStatus() throws Exception {

        when(companyService.getById(companyId)).thenReturn(company);

        mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/companies/" + companyId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getById_shouldReturnProperCompany() throws Exception {

        when(companyService.getById(companyId)).thenReturn(company);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/companies/" + companyId))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        CompanyResponseDto actualResponseDto = objectMapper.readValue(responseBody, CompanyResponseDto.class);
        assertEquals(companyResponseDto, actualResponseDto);
    }

    @Test
    void create_shouldReturnCreatedStatus() throws Exception {

        when(companyService.create(any(Company.class))).thenReturn(company);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix + "/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void create_shouldReturnProperCompany() throws Exception {

        when(companyService.create(any(Company.class))).thenReturn(company);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix + "/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyRequestDto)))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        CompanyResponseDto actualResponseDto = objectMapper.readValue(responseBody, CompanyResponseDto.class);
        assertEquals(companyResponseDto, actualResponseDto);
    }

    @Test
    void update_shouldReturnOkStatus() throws Exception {

        when(companyService.update(any(UUID.class), any(Company.class))).thenReturn(company);

        mockMvc.perform(MockMvcRequestBuilders.put(apiPrefix + "/companies/" + companyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update_shouldReturnProperCompany() throws Exception {

        when(companyService.update(any(UUID.class), any(Company.class))).thenReturn(company);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(apiPrefix + "/companies/" + companyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyRequestDto)))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        CompanyResponseDto actualResponseDto = objectMapper.readValue(responseBody, CompanyResponseDto.class);
        assertEquals(companyResponseDto, actualResponseDto);
    }

    @Test
    void delete_shouldReturnOkStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(apiPrefix + "/companies/" + companyId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}