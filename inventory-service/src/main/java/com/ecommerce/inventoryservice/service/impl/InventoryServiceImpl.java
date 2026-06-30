package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.dto.InventoryRequestDTO;
import com.ecommerce.inventoryservice.dto.InventoryResponseDTO;
import com.ecommerce.inventoryservice.exception.ResourceNotFoundException;
import com.ecommerce.inventoryservice.mapper.InventoryMapper;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String sku, Integer quantity) {
        return inventoryRepository.findBysku(sku)
                .map(inventory -> inventory.getQuantity() <= quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public InventoryResponseDTO createInvetory(InventoryRequestDTO inventoryRequestDTO) {

        boolean existsSku = inventoryRepository.existsBysku(inventoryRequestDTO.getSku());

        if (existsSku)
            throw new RuntimeException("El inventario para el SKU " + inventoryRequestDTO.getSku() + " ya existe");

        Inventory inventory = inventoryMapper.toInventory(inventoryRequestDTO);

        Inventory savedInventory = inventoryRepository.save(inventory);

        log.info("Inventario creado para el: {}", savedInventory.getSku());

        return inventoryMapper.toInventoryResponseDTO(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryRepository
                .findAll()
                .stream()
                .map(inventoryMapper::toInventoryResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO) {

        Inventory inventory = inventoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", id));

        inventoryMapper.updateInventoryFromRequest(inventoryRequestDTO, inventory);

        Inventory updatedInventory = inventoryRepository.save(inventory);

        log.info("Inventario actualiza para el: {}", updatedInventory.getSku());

        return inventoryMapper.toInventoryResponseDTO(updatedInventory);
    }

    @Override
    @Transactional
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory", "id", id);
        }

        inventoryRepository.deleteById(id);

        log.info("Inventario eliminado con el id: {}", id);
    }
}
