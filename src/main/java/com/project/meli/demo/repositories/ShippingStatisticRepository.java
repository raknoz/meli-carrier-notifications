package com.project.meli.demo.repositories;

import com.project.meli.demo.entities.ShippingStatisticEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ShippingStatisticRepository extends CrudRepository<ShippingStatisticEntity, Integer> {
    ShippingStatisticEntity getStatisticsByDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
