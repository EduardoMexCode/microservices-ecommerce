package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    List<ProductResponseDTO> getAllsProducts();
    ProductResponseDTO getProduct(String id);
    ProductResponseDTO updateProduct(String id, ProductRequestDTO requestDTO);
    void deleteProduct(String id);
}
