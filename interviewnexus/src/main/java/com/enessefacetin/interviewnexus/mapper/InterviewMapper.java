package com.enessefacetin.interviewnexus.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.entity.Question;
import com.enessefacetin.interviewnexus.model.request.InsertInterviewRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateInterviewRequest;
import com.enessefacetin.interviewnexus.model.response.InterviewDetailResponse;
import com.enessefacetin.interviewnexus.model.response.InterviewResponse;
import com.enessefacetin.interviewnexus.model.response.QuestionResponse;

import java.time.format.DateTimeFormatter; // Import DateTimeFormatter
import java.util.Locale; // Import Locale for defining locale-specific formats
import java.time.LocalDate; // Import LocalDate

import java.util.List;
import java.util.stream.Collectors;

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
    @Mapping(source = "interviewStatus", target = "status")
    InterviewResponse toResponse(Interview interview);

    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "profession.name", target = "professionName")
    @Mapping(source = "interviewStatus", target = "status")
    InterviewDetailResponse toDetailedResponse(Interview interview);

    default String mapLocalDateToString(LocalDate localDate) {
        Locale trlocale= Locale.forLanguageTag("tr_TR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", trlocale);
        // Format the LocalDate to string
        return localDate.format(formatter);
    }

    default List<QuestionResponse> mapQuestions(List<Question> questions) {
        return questions.stream()
                .map(QuestionMapper.INSTANCE::toResponse) // Utilize QuestionMapper to map questions
                .collect(Collectors.toList());
    }

    
}


