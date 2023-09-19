package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.model.Office;
import com.example.demointegracaomysqlsalesforce.repository.OfficeRepository;
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
public class OfficeController {

    private final OfficeRepository officeRepository;
    private final ObjectMapper objectMapper;

    public OfficeController(OfficeRepository officeRepository, ObjectMapper objectMapper) {
        this.officeRepository = officeRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Isso habilita a formatação bonita do JSON
    }

    @GetMapping(value = "/allOffices", produces = "application/json")
    public ResponseEntity<String> getAllOffices() {
        List<Office> offices = officeRepository.findAll();
        try {
            String json = objectMapper.writeValueAsString(offices);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }

}
