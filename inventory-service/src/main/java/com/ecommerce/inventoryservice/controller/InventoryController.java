package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.InventoryRequestDTO;
import com.ecommerce.inventoryservice.dto.InventoryResponseDTO;
import com.ecommerce.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String sku,
                             @RequestParam("quantity") Integer quantity) {
        return inventoryService.isInStock(sku, quantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDTO createInventory(@RequestBody @Valid InventoryRequestDTO inventoryRequestDTO) {
        return inventoryService.createInvetory(inventoryRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponseDTO updateInventory(@PathVariable Long id,
                                                @RequestBody @Valid InventoryRequestDTO inventoryRequestDTO) {
        return inventoryService.updateInventory(id, inventoryRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }

}
