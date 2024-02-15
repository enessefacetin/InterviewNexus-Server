package com.enessefacetin.interviewnexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enessefacetin.interviewnexus.model.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
