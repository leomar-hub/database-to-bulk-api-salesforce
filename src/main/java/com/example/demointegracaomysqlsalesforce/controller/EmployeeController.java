package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.model.Employee;
import com.example.demointegracaomysqlsalesforce.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    public EmployeeController(EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Isso habilita a formatação bonita do JSON
    }

    @GetMapping(value = "/allEmployees", produces = "application/json")
    public ResponseEntity<String> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        try {
            String json = objectMapper.writeValueAsString(employees);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }

}
