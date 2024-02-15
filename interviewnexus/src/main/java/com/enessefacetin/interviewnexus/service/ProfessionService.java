package com.enessefacetin.interviewnexus.service;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.repository.ProfessionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessionService {

    private final ProfessionRepository professionRepository;

    public void getAllProfessions() {
        professionRepository.findAll();
    }

    public void getProfessionById(Long id) {
        professionRepository.findById(id);
    }

    public void deleteProfessionById(Long id) {
        professionRepository.deleteById(id);
    }
}
