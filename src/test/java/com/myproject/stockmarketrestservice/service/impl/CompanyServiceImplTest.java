package com.myproject.stockmarketrestservice.service.impl;

import com.myproject.stockmarketrestservice.exception.ResourceNotFoundException;
import com.myproject.stockmarketrestservice.model.entity.Company;
import com.myproject.stockmarketrestservice.repository.CompanyRepository;
import com.myproject.stockmarketrestservice.service.abstraction.CompanyService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CompanyServiceImpl.class)
class CompanyServiceImplTest {

    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    private static final UUID companyId = UUID.randomUUID();
    private static final Company company = new Company();
    private static final Company updatedCompany = new Company();

    private static final Page<Company> companyPage = new PageImpl<>(List.of(
            new Company(), new Company(), new Company()
    ));

    @BeforeAll
    static void init() {

        company.setId(companyId);
        company.setName("Test Company");

        updatedCompany.setId(companyId);
        updatedCompany.setName("Updated Company");
    }

    @Test
    void getAll_shouldReturnPageOfCompanies() {

        Pageable pageable = PageRequest.of(0, 10);
        when(companyRepository.findAll(pageable)).thenReturn(companyPage);

        Page<Company> result = companyService.getAll(pageable);

        assertEquals(companyPage, result);
    }

    @Test
    void getById_shouldReturnCompany_ifCompanyExists() {

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        Company result = companyService.getById(companyId);

        assertEquals(company, result);

    }

    @Test
    void getById_shouldThrowException_ifCompanyDoesNotExist() {

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> companyService.getById(companyId));
    }

    @Test
    void create_shouldSaveAndReturnCompany() {

        when(companyRepository.save(company)).thenReturn(company);

        Company result = companyService.create(company);

        assertEquals(company, result);
        verify(companyRepository).save(company);
    }

    @Test
    void update_shouldSaveAndReturnUpdatedCompany() {

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);

        Company result = companyService.update(companyId, updatedCompany);

        assertEquals(updatedCompany, result);
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void update_shouldThrowException_ifCompanyDoesNotExist() {

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> companyService.update(companyId, updatedCompany));
    }

    @Test
    void delete_shouldRemoveCompany_ifCompanyExists() {

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        companyService.delete(companyId);

        verify(companyRepository).delete(company);
    }

    @Test
    void delete_shouldThrowException_ifCompanyDoesNotExist() {

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> companyService.delete(companyId));
    }
}
