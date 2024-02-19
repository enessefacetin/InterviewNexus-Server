package com.enessefacetin.interviewnexus.model.response;

import com.enessefacetin.interviewnexus.model.entity.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private String content;
    private String answer;
    private String status;
}
