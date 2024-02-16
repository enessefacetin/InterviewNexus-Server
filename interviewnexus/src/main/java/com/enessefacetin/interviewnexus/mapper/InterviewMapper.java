package com.enessefacetin.interviewnexus.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.request.InsertInterviewRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateInterviewRequest;
import com.enessefacetin.interviewnexus.model.response.InterviewResponse;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface InterviewMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(source = "questions", target = "questions")
    @Mapping(target = "interviewStatus", ignore = true)
    Interview toEntity(InsertInterviewRequest request);
    
    @AfterMapping
    default void linkQuestionsToInterview(@MappingTarget Interview interview, InsertInterviewRequest request) {
        if (interview.getQuestions() != null && !interview.getQuestions().isEmpty()) {
            interview.getQuestions().forEach(question -> question.setInterview(interview));
        }
    }


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "interviewStatus", ignore = true)
    Interview toEntity(UpdateInterviewRequest request);

    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "profession.name", target = "professionName")
    InterviewResponse toResponse(Interview interview);

    
}


