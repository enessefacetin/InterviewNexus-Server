package com.enessefacetin.interviewnexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.enessefacetin.interviewnexus.model.entity.Industry;
import com.enessefacetin.interviewnexus.model.request.InsertIndustryRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateIndustryRequest;
import com.enessefacetin.interviewnexus.model.response.IndustryResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndustryMapper {
    
    Industry toEntity(InsertIndustryRequest request);

    Industry toEntity(UpdateIndustryRequest request);

    IndustryResponse toResponse(Industry industry);
}
