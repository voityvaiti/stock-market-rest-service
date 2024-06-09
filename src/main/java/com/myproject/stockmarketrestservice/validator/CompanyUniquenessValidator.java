package com.myproject.stockmarketrestservice.validator;

import com.myproject.stockmarketrestservice.exception.UniqueConstraintsViolation;
import com.myproject.stockmarketrestservice.model.entity.Company;
import com.myproject.stockmarketrestservice.service.abstraction.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyUniquenessValidator {

    private final CompanyService companyService;


    public void validateNew(Company company) throws UniqueConstraintsViolation {

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(company, "company");

        validateRegistrationNumberOfNew(company, result);

        if(result.hasErrors()) {
            throw new UniqueConstraintsViolation(result);
        }
    }

    public void validateUpdated(UUID id, Company company) throws UniqueConstraintsViolation {

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(company, "company");

        validateRegistrationNumberOfUpdated(id, company, result);

        if(result.hasErrors()) {
            throw new UniqueConstraintsViolation(result);
        }
    }

    private void validateRegistrationNumberOfNew(Company company, BeanPropertyBindingResult result) {

        if (companyService.existByRegistrationNumber(company.getRegistrationNumber())) {
            result.rejectValue("registrationNumber", "company.registrationNumber", "Company with same registration number is already exists.");
        }

    }

    private void validateRegistrationNumberOfUpdated(UUID id, Company company, BeanPropertyBindingResult result) {

        companyService.findByRegistrationNumber(company.getRegistrationNumber())
                .filter(existingCompany -> !existingCompany.getId().equals(id))
                .ifPresent(existingCompany -> result.rejectValue("registrationNumber", "company.registrationNumber", "Company with same registration number already exists."));
    }
}
