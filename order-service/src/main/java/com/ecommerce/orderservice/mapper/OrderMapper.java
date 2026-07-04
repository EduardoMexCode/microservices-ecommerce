package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.OrderLineItemsRequestDTO;
import com.ecommerce.orderservice.dto.OrderLineItemsResponseDTO;
import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "orderListItemsDtoList", target = "orderListItemsList")
    Order toOrder(OrderRequestDTO orderRequestDTO);

    OrderLineItems toOrderLineItems(OrderLineItemsRequestDTO orderLineItemsRequestDTO);

    @Mapping(source = "orderLineItemsList", target = "orderLineItemsResponseDTOList")
    OrderResponseDTO toOrderResponseDTO(Order order);

    OrderLineItemsResponseDTO toOrderLineItemsRespomnseDTO(OrderLineItems orderLineItems);
}
