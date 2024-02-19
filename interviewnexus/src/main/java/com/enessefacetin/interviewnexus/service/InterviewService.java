package com.enessefacetin.interviewnexus.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.exception.EntityNotFoundException;
import com.enessefacetin.interviewnexus.mapper.InterviewMapper;
import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.entity.Status;
import com.enessefacetin.interviewnexus.model.entity.User;
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
import java.util.UUID;

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
    public List<InterviewResponse> getInterviewsByUserId() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = user.getId();
        var interviews = interviewRepository.findByUserId(userId);
        return interviews.stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InterviewResponse> getLastNInterviews(int n) {
        var pageable = PageRequest.of(0, n);
        return interviewRepository.findLastNInterviewsByStatus(Status.APPROVED, Status.APPROVED, pageable)
                                   .stream()
                                   .map(interviewMapper::toResponse)
                                   .collect(Collectors.toList());
    }

    @Transactional
    public InterviewDetailResponse getInterviewById(Long id) {
        var interview = interviewRepository.findInterviewDetailWithApprovedQuestions(id, Status.APPROVED)
        .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));
        
        return interviewMapper.toDetailedResponse(interview);
    }

    @Transactional
    public InterviewDetailResponse getUserInterviewById(Long id) {
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

    @Transactional
    public List<InterviewDetailResponse> getPendingInterviews() {
        List<Interview> pendingInterviews = interviewRepository.findByStatus(Status.PENDING);
        return pendingInterviews.stream()
            .map(interviewMapper::toDetailedResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void approveInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));
        interview.setInterviewStatus(Status.APPROVED);
        for (var question : interview.getQuestions()) {
            question.setQuestionStatus(Status.APPROVED);
        }
        interviewRepository.saveAndFlush(interview);
    }
    
    @Transactional
    public void rejectInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));
        interview.setInterviewStatus(Status.REJECTED);
        for (var question : interview.getQuestions()) {
            question.setQuestionStatus(Status.REJECTED);
        }
        interviewRepository.saveAndFlush(interview);
    }
}
