package com.project.meli.demo.repositories;

import com.project.meli.demo.entities.ShippingHistoricalEntity;
import com.project.meli.demo.entities.ShippingMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.project.meli.demo.utils.TestUtils.SHIPPING_ID;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_STATUS_READY_TO_SHIP_DESC;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_SUB_STATUS_READY_TO_SHIP_DESC;
import static com.project.meli.demo.utils.TestUtils.buildShippingMovementSubStatusNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShippingHistoricalRepositoryIT {
    @Autowired
    private ShippingHistoricalRepository shippingHistoricalRepository;

    @BeforeEach
    public void setUp() {
        /* Clear the Table because in the first test it will be insert a register and fail other one.*/
        shippingHistoricalRepository.deleteAll();
    }

    @DisplayName("Class: ShippingHistoricalRepository - method: save - flow: OK")
    @Test
    public void saveShippingHistoricalOnlyShippingCode() {
        //
        final ShippingHistoricalEntity entity =
                shippingHistoricalRepository.save(new ShippingHistoricalEntity(SHIPPING_ID, null, null));
        //When
        final Optional<ShippingHistoricalEntity> entityOptional = shippingHistoricalRepository.findById(entity.getId());
        //Then
        assertTrue(entityOptional.isPresent());
        final ShippingHistoricalEntity shippingHistoricalEntity = entityOptional.get();
        assertEquals(SHIPPING_ID, shippingHistoricalEntity.getShippingCode());
    }

    @DisplayName("Class: ShippingHistoricalRepository - method: update - flow: OK")
    @Test
    public void updateShippingHistorical() {
        //
        final ShippingMovement shippingMovement = buildShippingMovementSubStatusNotNull();
        final ShippingHistoricalEntity historicalEntity =
                new ShippingHistoricalEntity(SHIPPING_ID, shippingMovement.getStatus().toString(),
                        shippingMovement.getSubStatus().toString());
        //when
        final ShippingHistoricalEntity entity =
                shippingHistoricalRepository.save(historicalEntity);
        //Then
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getDateCreate());
        assertEquals(SHIPPING_ID, entity.getShippingCode());
        assertEquals(SHIPPING_STATUS_READY_TO_SHIP_DESC, entity.getStatus());
        assertEquals(SHIPPING_SUB_STATUS_READY_TO_SHIP_DESC, entity.getSubStatus());
    }
}
