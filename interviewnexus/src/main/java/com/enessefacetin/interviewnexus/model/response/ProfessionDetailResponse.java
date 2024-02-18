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
public class ProfessionDetailResponse {
    private Long id;
    private String name; 
    private List<InterviewResponse> interviews;
}
