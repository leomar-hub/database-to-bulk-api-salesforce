package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.model.Orderdetails;
import com.example.demointegracaomysqlsalesforce.repository.OrderdetailsRepository;
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
public class OrderdetailsController {

    private final OrderdetailsRepository orderdetailsRepository;

    private final ObjectMapper objectMapper;

    public OrderdetailsController(OrderdetailsRepository orderdetailsRepository, ObjectMapper objectMapper) {
        this.orderdetailsRepository = orderdetailsRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Isso habilita a formatação bonita do JSON
    }

    @GetMapping(value = "/allOrdersdetails", produces = "application/json")
    public ResponseEntity<String> getAllOrdersdetails() {
        List<Orderdetails> ordersdetails = orderdetailsRepository.findAll();
        try {
            String json = objectMapper.writeValueAsString(ordersdetails);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }

}
