package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.model.Order;
import com.example.demointegracaomysqlsalesforce.repository.OrderRepository;
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
public class OrderController {

    private final OrderRepository orderRepository;

    private final ObjectMapper objectMapper;

    public OrderController(OrderRepository orderRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Isso habilita a formatação bonita do JSON
    }

    @GetMapping(value = "/allOrders", produces = "application/json")
    public ResponseEntity<String> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        try {
            String json = objectMapper.writeValueAsString(orders);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }

}
