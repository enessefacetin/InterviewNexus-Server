package com.enessefacetin.interviewnexus.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.enessefacetin.interviewnexus.model.entity.Interview;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>{
    @Query("SELECT i FROM Interview i WHERE i.user.id = :userId")
    List<Interview> findByUserId(Long userId);

    @Query("SELECT i FROM Interview i ORDER BY i.interviewDate DESC")
    List<Interview> findLastNInterviews(Pageable pageable);


}
