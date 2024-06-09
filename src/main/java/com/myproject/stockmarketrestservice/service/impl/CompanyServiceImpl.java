package com.myproject.stockmarketrestservice.service.impl;

import com.myproject.stockmarketrestservice.exception.ResourceNotFoundException;
import com.myproject.stockmarketrestservice.mapper.CompanyMapper;
import com.myproject.stockmarketrestservice.model.entity.Company;
import com.myproject.stockmarketrestservice.repository.CompanyRepository;
import com.myproject.stockmarketrestservice.service.abstraction.CompanyService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);


    @Override
    public boolean existByRegistrationNumber(String registrationNumber) {
        return companyRepository.existsByRegistrationNumber(registrationNumber);
    }

    @Override
    public Optional<Company> findByRegistrationNumber(String registrationNumber) {
        return companyRepository.findByRegistrationNumber(registrationNumber);
    }

    @Override
    public Page<Company> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public Company getById(UUID id) {
        return getCompanyById(id);
    }

    @Override
    public Company create(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company update(UUID id, Company updatedCompany) {

        Company companyToUpdate = getCompanyById(id);

        return companyRepository.save(
                companyMapper.updateFields(updatedCompany, companyToUpdate)
        );
    }

    @Override
    public void delete(UUID id) {

        Company companyToDelete = getCompanyById(id);

        companyRepository.delete(companyToDelete);
    }


    private Company getCompanyById(UUID id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Company with id " + id + " not found")
        );
    }

}
