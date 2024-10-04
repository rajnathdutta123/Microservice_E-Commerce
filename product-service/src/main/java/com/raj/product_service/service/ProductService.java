package com.raj.product_service.service;

import com.raj.product_service.dto.ProductRequest;
import com.raj.product_service.dto.ProductResponse;
import com.raj.product_service.model.Product;
import com.raj.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class ProductService {

    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest)
    {
        Product product=Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        product=productRepository.save(product);
        log.info("Product {} is Created",product.getId());
       return convertIntoDto(product);
    }
    public List<ProductResponse> getAllProducts()
    {
        return productRepository.findAll().stream().map(this::convertIntoDto).collect(Collectors.toList());
    }
    public ProductResponse convertIntoDto(Product product)
    {
        if(product==null)
        {
            log.error("Product Object is Null");
            return null;
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
