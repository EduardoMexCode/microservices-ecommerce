package com.ecommerce.inventoryservice.mapper;

import com.ecommerce.inventoryservice.dto.InventoryRequestDTO;
import com.ecommerce.inventoryservice.dto.InventoryResponseDTO;
import com.ecommerce.inventoryservice.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toInventory(InventoryRequestDTO inventoryRequestDTO);

    @Mapping(target = "isStock", expression = "java(inventory.getQuantity() > 0)")
    InventoryResponseDTO toInventoryResponseDTO(Inventory inventory);

    @Mapping(target = "id", ignore = true)
    void updateInventoryFromRequest(InventoryRequestDTO inventoryRequestDTO, @MappingTarget Inventory inventory);
}
