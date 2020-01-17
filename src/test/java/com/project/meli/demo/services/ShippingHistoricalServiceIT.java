package com.project.meli.demo.services;

import com.project.meli.demo.entities.ShippingHistoricalEntity;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.repositories.ShippingHistoricalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.project.meli.demo.utils.TestUtils.SHIPPING_STATUS_READY_TO_SHIP_DESC;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_SUB_STATUS_READY_TO_SHIP_DESC;
import static com.project.meli.demo.utils.TestUtils.buildShippingMovementSubStatusNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShippingHistoricalServiceIT {
    @Autowired
    private ShippingHistoricalService shippingHistoricalService;
    @Autowired
    private ShippingHistoricalRepository shippingHistoricalRepository;

    @BeforeEach
    public void setUp() {
        shippingHistoricalRepository.deleteAll();
    }

    @DisplayName("Class: ShippingHistoricalService - method: shippingQueryRegister - flow: Ok")
    @Test
    public void shippingQueryRegisterOk() {
        //
        final String shippingCode = "95643OP";
        //When
        final ShippingHistoricalEntity historicalEntity = shippingHistoricalService.shippingQueryRegister(shippingCode);
        //Then
        assertNotNull(historicalEntity);
        assertNotNull(historicalEntity.getId());
        assertEquals(shippingCode, historicalEntity.getShippingCode());
        assertNull(historicalEntity.getDateUpdate());
        assertNotNull(historicalEntity.getDateCreate());
    }

    @DisplayName("Class: ShippingHistoricalService - method: shippingQueryRegister - flow: Ok")
    @Test
    public void shippingQueryRegisterUpdateOk() {
        //
        final String shippingCode = "95643OP";
        final ShippingMovement shippingMovement = buildShippingMovementSubStatusNotNull();
        final ShippingHistoricalEntity historicalEntity =
                new ShippingHistoricalEntity(shippingCode, shippingMovement.getStatus().toString(),
                        shippingMovement.getSubStatus().toString());
        //when
        final ShippingHistoricalEntity entityResponse =
                shippingHistoricalService.shippingQueryRegister(historicalEntity, shippingMovement);
        //Then
        assertNotNull(entityResponse);
        assertNotNull(entityResponse.getId());
        assertNotNull(entityResponse.getDateCreate());
        assertEquals(shippingCode, entityResponse.getShippingCode());
        assertNotNull(historicalEntity.getDateUpdate());
        assertEquals(SHIPPING_STATUS_READY_TO_SHIP_DESC, entityResponse.getStatus());
        assertEquals(SHIPPING_SUB_STATUS_READY_TO_SHIP_DESC, entityResponse.getSubStatus());
    }
}
