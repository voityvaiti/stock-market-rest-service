package com.myproject.stockmarketrestservice.mapper;

import com.myproject.stockmarketrestservice.dto.request.ReportDetailsRequestDto;
import com.myproject.stockmarketrestservice.dto.response.ReportDetailsResponseDto;
import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface ReportDetailsMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    ReportDetailsResponseDto mapToResponseDto(ReportDetails reportDetails);

    ReportDetails mapToReportDetails(ReportDetailsRequestDto reportDetailsRequestDto);


    @Named("objectIdToString")
    static String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toString() : null;
    }

}
