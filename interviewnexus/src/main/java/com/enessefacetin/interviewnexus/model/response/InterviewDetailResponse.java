package com.enessefacetin.interviewnexus.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDetailResponse {
    private String companyName;
    private String professionName;
    private String type;
    private int score;
    private String opinion;
    private String interviewDate;
    private List<QuestionResponse> questions;
}
