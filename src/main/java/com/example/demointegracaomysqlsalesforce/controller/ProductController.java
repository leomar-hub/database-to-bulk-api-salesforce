package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.model.Product;
import com.example.demointegracaomysqlsalesforce.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    public ProductController(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Isso habilita a formatação bonita do JSON
    }

    @GetMapping(value = "/allProducts", produces = "application/json")
    public ResponseEntity<String> getAllProducts() {
        List<Product> products = productRepository.findAll();
        try {
            String json = objectMapper.writeValueAsString(products);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }

}
