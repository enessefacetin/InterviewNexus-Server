package com.enessefacetin.interviewnexus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.entity.Question;
import com.enessefacetin.interviewnexus.model.request.InsertQuestionRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateQuestionRequest;
import com.enessefacetin.interviewnexus.model.response.QuestionResponse;
import com.enessefacetin.interviewnexus.service.QuestionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
@Tag(name = "Question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        var questionResponse = questionService.getQuestionById(id);
        return ResponseEntity.ok().body(questionResponse);
    }

    @PostMapping
    public ResponseEntity<String> createQuestion(@Valid @RequestBody InsertQuestionRequest question) {
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body("Question created with id: " + createdQuestion.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long id, @Valid @RequestBody UpdateQuestionRequest questionDetails) {
        Question updatedQuestion = questionService.updateQuestion(id, questionDetails);
        return ResponseEntity.ok().body("Question updated with id: " + updatedQuestion.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().body("Question deleted with id: " + id);
    }
}
