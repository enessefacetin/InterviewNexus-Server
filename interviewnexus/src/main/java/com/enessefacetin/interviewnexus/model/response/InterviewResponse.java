package com.enessefacetin.interviewnexus.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewResponse {
    private String companyName;
    private String professionName;
    private String type;
    private int score;
    private String opinion;
    private LocalDateTime interviewDate;

}
