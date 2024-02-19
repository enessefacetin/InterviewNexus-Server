package com.enessefacetin.interviewnexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enessefacetin.interviewnexus.model.entity.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {

}

