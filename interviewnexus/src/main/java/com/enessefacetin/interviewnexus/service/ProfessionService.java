package com.enessefacetin.interviewnexus.service;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.exception.EntityNotFoundException;
import com.enessefacetin.interviewnexus.mapper.ProfessionMapper;
import com.enessefacetin.interviewnexus.model.entity.Profession;
import com.enessefacetin.interviewnexus.model.request.InsertProfessionRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateProfessionRequest;
import com.enessefacetin.interviewnexus.model.response.ProfessionDetailResponse;
import com.enessefacetin.interviewnexus.model.response.ProfessionResponse;
import com.enessefacetin.interviewnexus.repository.ProfessionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessionService {

    private final ProfessionRepository professionRepository;
    private final ProfessionMapper professionMapper;


    @Transactional
    public List<ProfessionResponse> getAllProfessions() {
        var industries = professionRepository.findAll();
        return industries.stream()
                .map(professionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProfessionDetailResponse getProfessionById(Long id) {
        var profession = professionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Profession not found with id: " + id));

        return professionMapper.toDetailedResponse(profession);
    }

    @Transactional
    public Profession createProfession(InsertProfessionRequest professionRequest) {
        var profession = professionMapper.toEntity(professionRequest);
        return professionRepository.save(profession);
    }

    @Transactional
    public Profession updateProfession(Long id, UpdateProfessionRequest professionDetails) {
        var profession = professionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Profession not found with id: " + id));

        profession.setName(professionDetails.getName());        
        return professionRepository.save(profession);
    }

    @Transactional
    public void deleteProfession(Long id) {
        var profession = professionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Profession not found with id: " + id));
        professionRepository.delete(profession);
    }
}
