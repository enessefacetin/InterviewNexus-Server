package com.enessefacetin.interviewnexus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.request.InsertInterviewRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateInterviewRequest;
import com.enessefacetin.interviewnexus.model.response.InterviewDetailResponse;
import com.enessefacetin.interviewnexus.model.response.InterviewResponse;
import com.enessefacetin.interviewnexus.service.InterviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interview")
@RequiredArgsConstructor
@Tag(name = "Interview")
@PreAuthorize("hasAnyAuthority('User','Admin')")
public class InterviewController {
    private final InterviewService interviewService;

    @GetMapping
    public List<InterviewResponse> getAllInterviews() {
        return interviewService.getLastNInterviews(10);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewDetailResponse> getInterviewById(@PathVariable Long id) {
        var interviewResponse = interviewService.getInterviewById(id);
        return ResponseEntity.ok().body(interviewResponse);
    }

    @PostMapping
    public ResponseEntity<String> createInterview(@Valid @RequestBody InsertInterviewRequest interview) {
        Interview createdInterview = interviewService.createInterview(interview);
        return ResponseEntity.status(HttpStatus.CREATED).body("Interview created with id: " + createdInterview.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInterview(@PathVariable Long id, @Valid @RequestBody UpdateInterviewRequest interviewDetails) {
        Interview updatedInterview = interviewService.updateInterview(id, interviewDetails);
        return ResponseEntity.ok().body("Interview updated with id: " + updatedInterview.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.ok().body("Interview deleted with id: " + id);
    }
}
