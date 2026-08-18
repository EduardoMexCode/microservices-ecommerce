package com.ecommerce.orderservice.service.client;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface InventoryClient {

    @PutMapping("/api/v1/inventory/reduce/{sku}")
    String reduceStock(@PathVariable String sku, @RequestParam Integer quantity);
}
