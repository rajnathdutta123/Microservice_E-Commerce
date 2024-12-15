package com.raj.product_service.service;

import com.raj.api_gateway.dto.UserDTO;
import com.raj.product_service.dto.ProductRequest;
import com.raj.product_service.dto.ProductResponse;
import com.raj.product_service.model.Product;
import com.raj.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final JwtDecoder jwtDecoder;



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

    public UserDTO getUserFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token.replace("Bearer ", ""));
        String username = jwt.getSubject();
        String userId = jwt.getClaim("id");
        List<String> roles = jwt.getClaim("roles");
        String name=jwt.getClaim("name");
        boolean isActive=jwt.getClaim("isActive");
        return UserDTO.builder()
                .id(Integer.parseInt(userId))
                .name(name)
                .email(username)
                .roles(roles)
                .isActive(isActive)
                .build();
    }
}
