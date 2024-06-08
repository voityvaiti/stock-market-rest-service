package com.myproject.stockmarketrestservice.controller;

import com.myproject.stockmarketrestservice.dto.request.CompanyRequestDto;
import com.myproject.stockmarketrestservice.dto.response.CompanyResponseDto;
import com.myproject.stockmarketrestservice.mapper.CompanyMapper;
import com.myproject.stockmarketrestservice.model.entity.Company;
import com.myproject.stockmarketrestservice.service.abstraction.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("${api-prefix}/companies")
@RequiredArgsConstructor
@Tag(name = "Company")
public class CompanyController {

    private final CompanyService companyService;

    private final CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);


    @Operation(summary = "Get companies page", description = "Returns companies page by selected page number and page size")
    @GetMapping
    public ResponseEntity<Page<CompanyResponseDto>> getAllPage(@RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                               @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize) {

        Page<Company> companyPage = companyService.getAll(PageRequest.of(pageNumber, pageSize));

        return ResponseEntity.ok(companyPage.map(companyMapper::mapToResponseDto));
    }

    @Operation(summary = "Get company by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getById(@PathVariable UUID id) {

        Company company = companyService.getById(id);

        return ResponseEntity.ok(companyMapper.mapToResponseDto(company));
    }

    @Operation(summary = "Create company")
    @PostMapping
    public ResponseEntity<CompanyResponseDto> create(@RequestBody @Valid CompanyRequestDto companyRequestDto) {

        Company createdCompany = companyService.create(
                companyMapper.mapToCompany(companyRequestDto)
        );

        return new ResponseEntity<>(companyMapper.mapToResponseDto(createdCompany), HttpStatus.CREATED);
    }

    @Operation(summary = "Update company", description = "Updates all fields of selected by ID company")
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> update(@PathVariable UUID id, @RequestBody @Valid CompanyRequestDto companyRequestDto) {

        Company updatedCompany = companyService.update(
                id, companyMapper.mapToCompany(companyRequestDto)
        );

        return ResponseEntity.ok(companyMapper.mapToResponseDto(updatedCompany));
    }

    @Operation(summary = "Delete company by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        companyService.delete(id);

        return ResponseEntity.ok().build();
    }

}
