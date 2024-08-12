package com.example.microservices.products.service;


import com.example.microservices.products.dto.ProductRequest;
import com.example.microservices.products.dto.ProductResponse;
import com.example.microservices.products.model.Product;
import com.example.microservices.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product=Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product create Successfully");
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getSkuCode(),product.getPrice());
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getSkuCode(),product.getPrice()))
                .toList();
    }

}
