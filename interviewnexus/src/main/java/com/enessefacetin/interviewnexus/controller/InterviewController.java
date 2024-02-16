package com.enessefacetin.interviewnexus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.request.InsertInterviewRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateInterviewRequest;
import com.enessefacetin.interviewnexus.model.response.InterviewResponse;
import com.enessefacetin.interviewnexus.service.InterviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/interview")
@RequiredArgsConstructor
@Tag(name = "Interview")
public class InterviewController {
    private final InterviewService interviewService;

    @GetMapping
    public List<InterviewResponse> getAllInterviews() {
        return interviewService.getAllInterviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable Long id) {
        var interviewResponse = interviewService.getInterviewById(id);
        return ResponseEntity.ok().body(interviewResponse);
    }

    @PostMapping
    public ResponseEntity<String> createInterview(@RequestHeader (name="Authorization") String token, @Valid @RequestBody InsertInterviewRequest interview) {
        token = token.substring(7);
        Interview createdInterview = interviewService.createInterview(interview, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Interview created with id: " + createdInterview.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInterview(@RequestHeader (name="Authorization") String token, @PathVariable Long id, @Valid @RequestBody UpdateInterviewRequest interviewDetails) {
        token = token.substring(7);
        Interview updatedInterview = interviewService.updateInterview(id, interviewDetails, token);
        return ResponseEntity.ok().body("Interview updated with id: " + updatedInterview.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.ok().body("Interview deleted with id: " + id);
    }
}
