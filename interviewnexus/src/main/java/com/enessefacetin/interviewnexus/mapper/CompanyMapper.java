package com.enessefacetin.interviewnexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.enessefacetin.interviewnexus.model.entity.Company;
import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.request.InsertCompanyRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateCompanyRequest;
import com.enessefacetin.interviewnexus.model.response.CompanyDetailResponse;
import com.enessefacetin.interviewnexus.model.response.CompanyResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {QuestionMapper.class, InterviewMapper.class})
public interface CompanyMapper {
    
    Company toEntity(InsertCompanyRequest request);

    Company toEntity(UpdateCompanyRequest request);

    @Mapping(source = "industries", target = "industries")
    CompanyResponse toResponse(Company industry);

    @Mapping(target = "averageScore", source = "company", qualifiedByName = "calculateAverageScore")
    CompanyDetailResponse toDetailedResponse(Company company);

    @Named("calculateAverageScore")
    default double calculateAverageScore(Company company) {
        List<Interview> interviews = company.getInterviews(); // Assuming you have a method to get interviews for a company
        if (interviews == null || interviews.isEmpty()) {
            return -1.0; 
        }
        double sum = interviews.stream().mapToInt(Interview::getScore).sum();
        return (double) sum / interviews.size();
    }

}
