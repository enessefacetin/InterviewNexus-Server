package com.enessefacetin.interviewnexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enessefacetin.interviewnexus.model.entity.Profession;
import com.enessefacetin.interviewnexus.model.entity.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long>{
    @Query("SELECT DISTINCT p FROM Profession p JOIN p.interviews i JOIN i.questions q WHERE i.interviewStatus = :interviewStatus AND q.questionStatus = :questionStatus")
    List<Profession> findProfessionsWithApprovedInterviewAndQuestion(@Param("interviewStatus") Status interviewStatus, @Param("questionStatus") Status questionStatus);

}
