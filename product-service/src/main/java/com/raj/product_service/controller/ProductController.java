package com.raj.product_service.controller;

import com.raj.api_gateway.dto.UserDTO;
import com.raj.product_service.dto.ProductRequest;
import com.raj.product_service.dto.ProductResponse;
import com.raj.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest)
    {
        return productService.createProduct(productRequest);
    }
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/auth/user/get")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAuthAllProducts(@RequestHeader("Authorization") String token)
    {
        UserDTO user = productService.getUserFromToken(token);
        System.out.println("Email Id:"+user.getEmail());
        return productService.getAllProducts();
    }
}
