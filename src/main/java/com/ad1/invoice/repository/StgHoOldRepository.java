package com.ad1.invoice.repository;

import com.ad1.invoice.model.StaggingHoOld;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StgHoOldRepository extends JpaRepository<StaggingHoOld, Long> {

}
