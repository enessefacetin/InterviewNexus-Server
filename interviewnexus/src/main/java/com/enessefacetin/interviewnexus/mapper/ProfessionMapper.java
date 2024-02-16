package com.enessefacetin.interviewnexus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.enessefacetin.interviewnexus.model.entity.Profession;
import com.enessefacetin.interviewnexus.model.request.InsertProfessionRequest;
import com.enessefacetin.interviewnexus.model.request.UpdateProfessionRequest;
import com.enessefacetin.interviewnexus.model.response.ProfessionResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfessionMapper {
    Profession toEntity(InsertProfessionRequest request);

    Profession toEntity(UpdateProfessionRequest request);

    ProfessionResponse toResponse(Profession profession);
}
