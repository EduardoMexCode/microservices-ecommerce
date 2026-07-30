package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.OrderLineItemsResponseDTO;
import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Set new order...");


        List<OrderLineItems> orderLineItems = orderRequestDTO.getOrderListItemsDtoList()
                .stream()
                .map(orderMapper::toOrderLineItems)
                .toList();

        Order order = orderMapper.toOrder(orderRequestDTO);
        order.setOrderLineItemsList(orderLineItems);
        order.setOrderNumber(UUID.randomUUID().toString());

        Order orderSaved = orderRepository.save(order);

        log.info("Order saved with success. ID: {}", orderSaved.getId());

        return orderMapper.toOrderResponseDTO(orderSaved);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toOrderResponseDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        return orderMapper.toOrderResponseDTO(order);
    }

    @Override
    public void deleteOrderById(Long id) {

        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", "id", id);
        }

        orderRepository.deleteById(id);
        log.info("Order deleted. ID: {}", id);
    }
}
