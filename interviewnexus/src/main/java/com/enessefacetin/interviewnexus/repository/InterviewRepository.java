package com.enessefacetin.interviewnexus.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enessefacetin.interviewnexus.model.entity.Interview;
import com.enessefacetin.interviewnexus.model.entity.Status;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>{
    @Query("SELECT i FROM Interview i WHERE i.user.id = :userId")
    List<Interview> findByUserId(UUID userId);

    @Query("SELECT DISTINCT i FROM Interview i JOIN i.questions q WHERE i.interviewStatus = :interviewStatus AND q.questionStatus = :questionStatus ORDER BY i.interviewDate DESC")
    List<Interview> findLastNInterviewsByStatus(@Param("interviewStatus") Status interviewStatus, @Param("questionStatus") Status questionStatus, Pageable pageable);

    @Query("SELECT i FROM Interview i WHERE i.interviewStatus = :interviewStatus")
    List<Interview> findByStatus(@Param("interviewStatus") Status interviewStatus);

    @Query("SELECT DISTINCT i FROM Interview i JOIN FETCH i.questions q WHERE i.id = :interviewId AND q.questionStatus = :questionStatus")
    Optional<Interview> findInterviewDetailWithApprovedQuestions(@Param("interviewId") Long interviewId, @Param("questionStatus") Status questionStatus);


}
