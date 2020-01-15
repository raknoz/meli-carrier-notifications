package com.project.meli.demo.repositories;

import com.project.meli.demo.entities.ShippingHistoricalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends CrudRepository<ShippingHistoricalRecord, Long> {
    Page<ShippingHistoricalRecord> findAll(Pageable pageable);
}
