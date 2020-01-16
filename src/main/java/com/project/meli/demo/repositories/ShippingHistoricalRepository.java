package com.project.meli.demo.repositories;

import com.project.meli.demo.entities.ShippingHistoricalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingHistoricalRepository extends CrudRepository<ShippingHistoricalEntity, Long> {
}
