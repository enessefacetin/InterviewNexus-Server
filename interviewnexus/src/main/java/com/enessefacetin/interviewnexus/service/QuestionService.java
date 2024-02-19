package com.enessefacetin.interviewnexus.service;

import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.exception.EntityNotFoundException;
import com.enessefacetin.interviewnexus.mapper.QuestionMapper;
import com.enessefacetin.interviewnexus.model.entity.Question;
import com.enessefacetin.interviewnexus.model.request.InsertQuestionRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateQuestionRequest;
import com.enessefacetin.interviewnexus.model.response.QuestionResponse;
import com.enessefacetin.interviewnexus.repository.QuestionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public List<QuestionResponse> getAllQuestions() {
        var industries = questionRepository.findAll();
        return industries.stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionResponse getQuestionById(Long id) {
        var question = questionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));

        return questionMapper.toResponse(question);
    }

    @Transactional
    public Question createQuestion(InsertQuestionRequest questionRequest) {
        var question = questionMapper.toEntity(questionRequest);
        return questionRepository.save(question);
    }

    @Transactional
    public Question updateQuestion(Long id, UpdateQuestionRequest questionDetails) {
        var question = questionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));

        question.setContent(questionDetails.getContent());
        question.setAnswer(questionDetails.getAnswer());
        return questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        var question = questionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));
        questionRepository.delete(question);
    }
}
