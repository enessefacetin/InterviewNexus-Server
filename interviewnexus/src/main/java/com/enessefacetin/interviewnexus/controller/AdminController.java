package com.enessefacetin.interviewnexus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.response.InterviewDetailResponse;
import com.enessefacetin.interviewnexus.model.response.InterviewResponse;
import com.enessefacetin.interviewnexus.service.InterviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin")
@PreAuthorize("hasAnyAuthority('Admin')")
public class AdminController {
    private final InterviewService interviewService;

    // Endpoint to get a list of pending interviews
    @GetMapping("/interviews")
    public ResponseEntity<List<InterviewDetailResponse>> getPendingInterviews() {
        List<InterviewDetailResponse> pendingInterviews = interviewService.getPendingInterviews();
        return ResponseEntity.ok(pendingInterviews);
    }

    // Endpoint to approve an interview
    @PutMapping("/interviews/{id}/approve")
    public ResponseEntity<Void> approveInterview(@PathVariable Long id) {
        interviewService.approveInterview(id);
        return ResponseEntity.ok().build();
    }

    // Endpoint to reject an interview
    @PutMapping("/interviews/{id}/reject")
    public ResponseEntity<Void> rejectInterview(@PathVariable Long id) {
        interviewService.rejectInterview(id);
        return ResponseEntity.ok().build();
    }
}
