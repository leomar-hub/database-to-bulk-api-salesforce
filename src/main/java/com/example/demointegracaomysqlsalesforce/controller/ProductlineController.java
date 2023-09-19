package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.model.Productline;
import com.example.demointegracaomysqlsalesforce.repository.ProductlineRepository;
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
public class ProductlineController {

    private final ProductlineRepository productlineRepository;

    private final ObjectMapper objectMapper;

    public ProductlineController(ProductlineRepository productlineRepository, ObjectMapper objectMapper) {
        this.productlineRepository = productlineRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Isso habilita a formatação bonita do JSON
    }

    @GetMapping(value = "/allProductslines", produces = "application/json")
    public ResponseEntity<String> getAllProductslines() {
        List<Productline> productlines = productlineRepository.findAll();
        try {
            String json = objectMapper.writeValueAsString(productlines);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }

}
