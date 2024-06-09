package com.myproject.stockmarketrestservice.service.abstraction;

import com.myproject.stockmarketrestservice.model.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService {

    boolean existByRegistrationNumber(String registrationNumber);

    Optional<Company> findByRegistrationNumber(String registrationNumber);

    Page<Company> getAll(Pageable pageable);

    Company getById(UUID id);

    Company create(Company company);

    Company update(UUID id, Company company);

    void delete(UUID id);

}
