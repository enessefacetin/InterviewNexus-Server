package com.enessefacetin.interviewnexus.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.exception.EntityNotFoundException;
import com.enessefacetin.interviewnexus.mapper.InterviewMapper;
import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.request.InsertInterviewRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateInterviewRequest;
import com.enessefacetin.interviewnexus.model.response.InterviewDetailResponse;
import com.enessefacetin.interviewnexus.model.response.InterviewResponse;
import com.enessefacetin.interviewnexus.repository.CompanyRepository;
import com.enessefacetin.interviewnexus.repository.InterviewRepository;
import com.enessefacetin.interviewnexus.repository.ProfessionRepository;
import com.enessefacetin.interviewnexus.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final CompanyRepository companyRepository;
    private final ProfessionRepository professionRepository;
    private final InterviewMapper interviewMapper;
    private final UserRepository userRepository;

    @Transactional
    public List<InterviewResponse> getAllInterviews() {
        var interviews = interviewRepository.findAll();
        return interviews.stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InterviewResponse> getInterviewsByUserId(Long userId) {
        var interviews = interviewRepository.findByUserId(userId);
        return interviews.stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InterviewResponse> getLastNInterviews(int n) {
        var pageable = PageRequest.of(0, n);
        return interviewRepository.findLastNInterviews(pageable)
                                   .stream()
                                   .map(interviewMapper::toResponse)
                                   .collect(Collectors.toList());
    }

    @Transactional
    public InterviewDetailResponse getInterviewById(Long id) {
        var interview = interviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));

        return interviewMapper.toDetailedResponse(interview);
    }

    @Transactional
    public Interview createInterview(InsertInterviewRequest interviewRequest) {
        var interview = interviewMapper.toEntity(interviewRequest);
        var userEmail =SecurityContextHolder.getContext().getAuthentication().getName();

        //var userEmail = jwtService.extractUserName(userToken);
        var user = userRepository.findByEmail(userEmail).get();
        interview.setUser(user);

        var profession = professionRepository.findById(interviewRequest.getProfessionId())
        .orElseThrow(() -> new EntityNotFoundException("Profession not found with id: " + interviewRequest.getProfessionId()));
        interview.setProfession(profession);

        var company = companyRepository.findById(interviewRequest.getCompanyId())
        .orElseThrow(() -> new EntityNotFoundException("Industry not found with id: " + interviewRequest.getCompanyId()));
        interview.setCompany(company);
    
        return interviewRepository.save(interview);
    }

    @Transactional
    public Interview updateInterview(Long id, UpdateInterviewRequest interviewDetails) {
        
        var interview = interviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));
        
        if(interview.getProfession().getId() != interviewDetails.getProfessionId()){
            var profession = professionRepository.findById(interviewDetails.getProfessionId())
            .orElseThrow(() -> new EntityNotFoundException("Profession not found with id: " + interviewDetails.getProfessionId()));
            interview.setProfession(profession);
        } 

        if (interview.getCompany().getId() != interviewDetails.getCompanyId()) {
            var company = companyRepository.findById(interviewDetails.getCompanyId())
            .orElseThrow(() -> new EntityNotFoundException("Industry not found with id: " + interviewDetails.getCompanyId()));
            interview.setCompany(company);
        }
       
        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(userEmail).get();

        interview.setUser(user);
        interview.setType(interviewDetails.getType());
        interview.setScore(interviewDetails.getScore());
        interview.setOpinion(interviewDetails.getOpinion());
        interview.setInterviewDate(interviewDetails.getInterviewDate());

        return interviewRepository.save(interview);
    }

    @Transactional
    public void deleteInterview(Long id) {
        var interview = interviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));
        interviewRepository.delete(interview);
    }
}
