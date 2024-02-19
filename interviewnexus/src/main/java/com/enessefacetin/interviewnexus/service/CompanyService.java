package com.enessefacetin.interviewnexus.service;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.exception.EntityNotFoundException;
import com.enessefacetin.interviewnexus.mapper.CompanyMapper;
import com.enessefacetin.interviewnexus.model.entity.Company;
import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.entity.Status;
import com.enessefacetin.interviewnexus.model.request.InsertCompanyRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateCompanyRequest;
import com.enessefacetin.interviewnexus.model.response.CompanyDetailResponse;
import com.enessefacetin.interviewnexus.model.response.CompanyResponse;
import com.enessefacetin.interviewnexus.repository.CompanyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional
    public List<CompanyResponse> getAllCompanies() {
        var companies = companyRepository.findCompaniesWithApprovedInterviewAndQuestion(Status.APPROVED, Status.APPROVED);
        return companies.stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CompanyDetailResponse getCompanyById(Long id) {
        var company = companyRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));
        
        List<Interview> filteredInterviews = company.getInterviews().stream()
            .filter(i -> i.getInterviewStatus() == Status.APPROVED)
            .filter(i -> i.getQuestions().stream().anyMatch(q -> q.getQuestionStatus() == Status.APPROVED))
            .collect(Collectors.toList());
        company.setInterviews(filteredInterviews);
        return companyMapper.toDetailedResponse(company);
    }

    @Transactional
    public Company createCompany(InsertCompanyRequest companyRequest) {
        var company = companyMapper.toEntity(companyRequest);
        return companyRepository.save(company);
    }

    @Transactional
    public Company updateCompany(Long id, UpdateCompanyRequest companyDetails) {
        var company = companyRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        company.setName(companyDetails.getName());        
        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(Long id) {
        var company = companyRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));
        companyRepository.delete(company);
    }

}
