package com.project.meli.demo.services;

import com.project.meli.demo.entities.ShippingHistoricalEntity;
import com.project.meli.demo.repositories.ShippingHistoricalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.meli.demo.utils.TestUtils.SHIPPING_ID;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_STATUS_READY_TO_SHIP;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_SUB_STATUS_DELIVERED_NULL;
import static com.project.meli.demo.utils.TestUtils.buildShippingMovementSubStatusNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShippingHistoricalServiceTest {
    @InjectMocks
    private ShippingHistoricalService shippingHistoricalService;
    @Mock
    private ShippingHistoricalRepository shippingHistoricalRepository;

    @DisplayName("Class: ShippingHistoricalService - method: shippingQueryRegister - flow: Ok")
    @Test
    public void shippingQueryRegisterOkTest() {
        //
        final ShippingHistoricalEntity entity =
                new ShippingHistoricalEntity(SHIPPING_ID, SHIPPING_STATUS_READY_TO_SHIP, SHIPPING_SUB_STATUS_DELIVERED_NULL);
        //Given
        when(shippingHistoricalRepository.save(any(ShippingHistoricalEntity.class))).thenReturn(entity);
        //When
        final ShippingHistoricalEntity response = shippingHistoricalService.shippingQueryRegister(SHIPPING_ID);
        //Then
        assertNotNull(response);
        assertEquals(SHIPPING_ID, response.getShippingCode());
    }

    @DisplayName("Class: ShippingHistoricalService - method: shippingQueryRegister - flow: Ok")
    @Test
    public void shippingQueryRegisterUpdateOkTest() {
        //
        final ShippingHistoricalEntity entityParameter =
                new ShippingHistoricalEntity(SHIPPING_ID, SHIPPING_STATUS_READY_TO_SHIP, SHIPPING_SUB_STATUS_DELIVERED_NULL);
        //Given
        when(shippingHistoricalRepository.save(any(ShippingHistoricalEntity.class))).thenReturn(mock(ShippingHistoricalEntity.class));
        //When
        shippingHistoricalService.shippingQueryRegister(entityParameter, buildShippingMovementSubStatusNull());
        //Then
        verify(shippingHistoricalRepository, atLeastOnce()).save(any(ShippingHistoricalEntity.class));
    }
}
