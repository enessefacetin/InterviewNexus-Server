package com.enessefacetin.interviewnexus.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailResponse {
    private Long id;
    private String name; 
    private Long averageScore;
    private List<IndustryResponse> industries;
    private List<InterviewResponse> interviews;
}
