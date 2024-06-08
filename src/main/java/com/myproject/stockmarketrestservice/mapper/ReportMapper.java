package com.myproject.stockmarketrestservice.mapper;

import com.myproject.stockmarketrestservice.dto.request.ReportRequestDto;
import com.myproject.stockmarketrestservice.dto.response.ReportResponseDto;
import com.myproject.stockmarketrestservice.model.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "updatedReport")
    Report updateFields(Report updatedReport, @MappingTarget Report reportToUpdate);

    ReportResponseDto mapToResponseDto(Report report);

    @Mapping(target = "company.id", source = "companyId")
    Report mapToReport(ReportRequestDto reportRequestDto);

}
