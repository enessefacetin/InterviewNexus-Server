package com.enessefacetin.interviewnexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.enessefacetin.interviewnexus.model.entity.Company;
import com.enessefacetin.interviewnexus.model.request.InsertCompanyRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateCompanyRequest;
import com.enessefacetin.interviewnexus.model.response.CompanyResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
    
    Company toEntity(InsertCompanyRequest request);

    Company toEntity(UpdateCompanyRequest request);

    CompanyResponse toResponse(Company industry);
}
