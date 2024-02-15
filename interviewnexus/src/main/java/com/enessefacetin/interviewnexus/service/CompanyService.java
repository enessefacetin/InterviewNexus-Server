package com.enessefacetin.interviewnexus.service;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void getAllCompanies() {
        companyRepository.findAll();
    }

    public void getCompanyById(Long id) {
        companyRepository.findById(id);
    }

    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

}
