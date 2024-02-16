package com.enessefacetin.interviewnexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.enessefacetin.interviewnexus.model.entity.Question;
import com.enessefacetin.interviewnexus.model.request.InsertQuestionRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateQuestionRequest;
import com.enessefacetin.interviewnexus.model.response.QuestionResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    @Mapping(target = "questionStatus", ignore = true)
    @Mapping(target = "interview", ignore = true)
    Question toEntity(InsertQuestionRequest request);

    @Mapping(target = "questionStatus", ignore = true)
    @Mapping(target = "interview", ignore = true)
    Question toEntity(UpdateQuestionRequest request);

    QuestionResponse toResponse(Question question);

}