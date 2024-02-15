package com.enessefacetin.interviewnexus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.exception.EntityNotFoundException;
import com.enessefacetin.interviewnexus.mapper.IndustryMapper;
import com.enessefacetin.interviewnexus.model.entity.Industry;
import com.enessefacetin.interviewnexus.model.request.InsertIndustryRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateIndustryRequest;
import com.enessefacetin.interviewnexus.model.response.IndustryResponse;
import com.enessefacetin.interviewnexus.repository.IndustryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndustryService {

    private final IndustryRepository industryRepository;
    private final IndustryMapper industryMapper;


    public List<IndustryResponse> getAllIndustries() {
        var industries = industryRepository.findAll();
        return industries.stream()
                .map(industryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public IndustryResponse getIndustryById(Long id) {
        var industry = industryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Industry not found with id: " + id));

        return industryMapper.toResponse(industry);
    }

    public Industry createIndustry(InsertIndustryRequest industryRequest) {
        var industry = industryMapper.toEntity(industryRequest);
        return industryRepository.save(industry);
    }

    public Industry updateIndustry(Long id, UpdateIndustryRequest industryDetails) {
        var industry = industryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Industry not found with id: " + id));

        industry.setName(industryDetails.getName());        
        return industryRepository.save(industry);
    }

    public void deleteIndustry(Long id) {
        var industry = industryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Industry not found with id: " + id));
        industryRepository.delete(industry);
    }
}
