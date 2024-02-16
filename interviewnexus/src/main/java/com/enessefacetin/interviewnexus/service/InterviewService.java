package com.enessefacetin.interviewnexus.service;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.exception.EntityNotFoundException;
import com.enessefacetin.interviewnexus.mapper.InterviewMapper;
import com.enessefacetin.interviewnexus.model.entity.Company;
import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.entity.Profession;
import com.enessefacetin.interviewnexus.model.request.InsertInterviewRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateInterviewRequest;
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
    private final JWTService jwtService;
    private final UserRepository userRepository;



    public List<InterviewResponse> getAllInterviews() {
        var industries = interviewRepository.findAll();
        return industries.stream()
                .map(interviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    public InterviewResponse getInterviewById(Long id) {
        var interview = interviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));

        return interviewMapper.toResponse(interview);
    }

    @Transactional
    public Interview createInterview(InsertInterviewRequest interviewRequest, String userToken) {
        var interview = interviewMapper.toEntity(interviewRequest);
        
        var userEmail = jwtService.extractUserName(userToken);
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

    public Interview updateInterview(Long id, UpdateInterviewRequest interviewDetails, String userToken) {
        
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
       
        var userEmail = jwtService.extractUserName(userToken);
        var user = userRepository.findByEmail(userEmail).get();

        interview.setUser(user);
        interview.setType(interviewDetails.getType());
        interview.setScore(interviewDetails.getScore());
        interview.setOpinion(interviewDetails.getOpinion());
        interview.setInterviewDate(interviewDetails.getInterviewDate());

        return interviewRepository.save(interview);
    }

    public void deleteInterview(Long id) {
        var interview = interviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + id));
        interviewRepository.delete(interview);
    }
}
