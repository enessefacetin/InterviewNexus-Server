package com.enessefacetin.interviewnexus.model.request;

import com.enessefacetin.interviewnexus.model.entity.InterviewType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsertInterviewRequest {
    private Long companyId;
    private Long professionId;
    @Enumerated(EnumType.STRING)
    private InterviewType type; 
    private int score;
    private String opinion;
    private LocalDate interviewDate;
    private List<InsertQuestionRequest> questions;
}
