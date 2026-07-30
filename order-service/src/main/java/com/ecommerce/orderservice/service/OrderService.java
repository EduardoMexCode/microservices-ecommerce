package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO); // Create

    List<OrderResponseDTO> getAllOrders(); // Read All

    OrderResponseDTO getOrderById(Long id); // Read one

    void deleteOrderById(Long id); // Delete
}
