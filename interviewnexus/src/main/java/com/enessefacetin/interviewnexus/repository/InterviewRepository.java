package com.enessefacetin.interviewnexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enessefacetin.interviewnexus.model.entity.Interview;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>{

}
