package com.enessefacetin.interviewnexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.enessefacetin.interviewnexus.model.entity.Company;
import com.enessefacetin.interviewnexus.model.entity.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT DISTINCT c FROM Company c JOIN c.interviews i JOIN i.questions q WHERE i.interviewStatus = :interviewStatus AND q.questionStatus = :questionStatus")
    List<Company> findCompaniesWithApprovedInterviewAndQuestion(@Param("interviewStatus") Status interviewStatus, @Param("questionStatus") Status questionStatus);

}
