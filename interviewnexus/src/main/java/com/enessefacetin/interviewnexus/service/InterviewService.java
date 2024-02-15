package com.enessefacetin.interviewnexus.service;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.repository.InterviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;

    public void getAllInterviews() {
        interviewRepository.findAll();
    }

    public void getInterviewById(Long id) {
        interviewRepository.findById(id);
    }

    public void deleteInterviewById(Long id) {
        interviewRepository.deleteById(id);
    }
}
