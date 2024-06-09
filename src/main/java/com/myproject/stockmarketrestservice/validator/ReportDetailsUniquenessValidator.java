package com.myproject.stockmarketrestservice.validator;

import com.myproject.stockmarketrestservice.exception.UniqueConstraintsViolation;
import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import com.myproject.stockmarketrestservice.service.abstraction.ReportDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

@Component
@RequiredArgsConstructor
public class ReportDetailsUniquenessValidator {

    private final ReportDetailsService reportDetailsService;


    public void validate(ReportDetails reportDetails) throws UniqueConstraintsViolation {

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(reportDetails, "reportDetails");

        validateReportId(reportDetails, result);

        if(result.hasErrors()) {
            throw new UniqueConstraintsViolation(result);
        }
    }

    private void validateReportId(ReportDetails reportDetails, BeanPropertyBindingResult result) {

        if (reportDetailsService.existsWithReportId(reportDetails.getReportId())) {
            result.rejectValue("reportId", "reportDetails.reportId", "ReportDetails with same report id is already exists.");
        }

    }
}