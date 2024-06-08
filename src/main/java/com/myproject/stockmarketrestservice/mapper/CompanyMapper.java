package com.myproject.stockmarketrestservice.mapper;

import com.myproject.stockmarketrestservice.dto.request.CompanyRequestDto;
import com.myproject.stockmarketrestservice.dto.response.CompanyResponseDto;
import com.myproject.stockmarketrestservice.model.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "updatedCompany")
    Company updateFields(Company updatedCompany, @MappingTarget Company companyToUpdate);

    CompanyResponseDto mapToResponseDto(Company company);

    @Mapping(target = "id", ignore = true)
    Company mapToCompany(CompanyRequestDto companyRequestDto);

}
