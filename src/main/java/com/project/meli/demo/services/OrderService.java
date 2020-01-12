package com.project.meli.demo.services;

import com.project.meli.demo.entities.OrderStatus;
import com.project.meli.demo.entities.OrderSubStatus;
import com.project.meli.demo.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Method to register the consulting of each order.
     *
     * @param orderId identification of the order.
     * @param orderStatus Last status of the order.
     * @param orderSubStatus Las subStatus of the order.
     */
    public void registerOrder(final String orderId, final OrderStatus orderStatus, final OrderSubStatus orderSubStatus){

        //orderRepository.save(new Order());
    }


}
