package com.myproject.stockmarketrestservice.repository;

import com.myproject.stockmarketrestservice.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsByRegistrationNumber(String registrationNumber);

    Optional<Company> findByRegistrationNumber(String registrationNumber);

}
