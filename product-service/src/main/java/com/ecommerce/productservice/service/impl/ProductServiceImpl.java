package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        Product product = mapper.toProduct(requestDTO);

        Product productCreated = repository.save(product);

        return mapper.toProductResponseDTO(productCreated);
    }

    @Override
    public List<ProductResponseDTO> getAllsProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProduct(String id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto no encontrado con el id: " + id)
        );

        return mapper.toProductResponseDTO(product);

//        return productOptional.map(mapper::toProductResponseDTO).orElse(null);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO requestDTO) {
        Product product = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto no encontrado con el id: " + id)
        );

        mapper.updateProductFromRequest(requestDTO, product);

        Product updatedProduct = repository.save(product);

        return mapper.toProductResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con el id: " + id);
        }

        repository.deleteById(id);
    }
}
