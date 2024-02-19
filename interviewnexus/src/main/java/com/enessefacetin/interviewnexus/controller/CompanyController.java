package com.enessefacetin.interviewnexus.controller;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Security;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.entity.Company;
import com.enessefacetin.interviewnexus.model.request.InsertCompanyRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateCompanyRequest;
import com.enessefacetin.interviewnexus.model.response.CompanyDetailResponse;
import com.enessefacetin.interviewnexus.model.response.CompanyResponse;
import com.enessefacetin.interviewnexus.service.CompanyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
@Tag(name = "Company")
@PreAuthorize("hasAnyAuthority('User', 'Admin')")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailResponse> getCompanyById(@PathVariable Long id) {
        var companyResponse = companyService.getCompanyById(id);
        return ResponseEntity.ok().body(companyResponse);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@Valid @RequestBody InsertCompanyRequest company) {
        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body("Company created with id: " + createdCompany.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @Valid @RequestBody UpdateCompanyRequest companyDetails) {
        Company updatedCompany = companyService.updateCompany(id, companyDetails);
        return ResponseEntity.ok().body("Company updated with id: " + updatedCompany.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().body("Company deleted with id: " + id);
    }
}
