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
    // 1.- De Request a Entidad
    @Mapping(source = "orderListItemsDtoList", target = "orderLineItemsList")
    Order toOrder(OrderRequestDTO orderRequestDTO);
    // (MapStruct usará este método para convertir cada elemento de la lista del Request)
    OrderLineItems toOrderLineItems(OrderLineItemsRequestDTO orderLineItemsRequestDTO);

    // 2.- De Entidad a Response
    @Mapping(source = "orderLineItemsList", target = "orderLineItemsResponseDTOList")
    OrderResponseDTO toOrderResponseDTO(Order order);
    // (MapStruct usará este método para convertir cada elemento de la lista hacia el Response )
    OrderLineItemsResponseDTO toOrderLineItemsRespomnseDTO(OrderLineItems orderLineItems);
}
