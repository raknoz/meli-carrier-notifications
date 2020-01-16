package com.project.meli.demo.services;

import com.project.meli.demo.entities.ShippingHistoricalEntity;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.repositories.ShippingHistoricalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShippingHistoricalService {
    private final Logger logger = LoggerFactory.getLogger(ShippingHistoricalService.class);
    private final ShippingHistoricalRepository shippingHistoricalRepository;

    public ShippingHistoricalService(final ShippingHistoricalRepository shippingHistoricalRepository) {
        this.shippingHistoricalRepository = shippingHistoricalRepository;
    }

    /**
     * Service to save information when start the process.
     *
     * @param shippingCode Id of the shipping.
     * @return Object persisted on the dataBase.
     */
    public ShippingHistoricalEntity shippingQueryRegister(final String shippingCode) {
        logger.info(String.format("Save query of shipping code: %s", shippingCode));
        return shippingHistoricalRepository.save(new ShippingHistoricalEntity(shippingCode, null, null));
    }

    /**
     * Service to save in historical_query_shipping, the information about the last status.
     *
     * @param shippingHistorical Object to update status and save it.
     * @param shippingMovement   Entity with the last status of the Shipping.
     */
    public void shippingQueryRegister(final ShippingHistoricalEntity shippingHistorical, final ShippingMovement shippingMovement) {
        logger.info(String.format("Update register of shipping code: %s", shippingHistorical.getShippingCode()));
        shippingHistorical.setStatus(shippingMovement.getStatus().toString());
        shippingHistorical.setSubStatus(shippingMovement.getSubStatus().toString());
        shippingHistorical.setDateUpdate(LocalDateTime.now());
        shippingHistoricalRepository.save(shippingHistorical);
    }
}
