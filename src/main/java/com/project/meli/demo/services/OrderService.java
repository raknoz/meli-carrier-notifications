package com.project.meli.demo.services;

import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    /**
     * Method to register the consulting of each order.
     *
     * @param orderId        identification of the order.
     * @param shippingStatus    Last status of the order.
     * @param shippingSubStatus Las subStatus of the order.
     */
    public void registerOrder(final String orderId, final ShippingStatus shippingStatus, final ShippingSubStatus shippingSubStatus) {

        //orderRepository.save(new Order());
    }


}
