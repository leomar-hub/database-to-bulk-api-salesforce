package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.model.Customer;
import com.example.demointegracaomysqlsalesforce.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@RestController
@Controller
@Component
@RequestMapping("/api")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    private String fileName;

    public CustomerController(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Isso habilita a formatação bonita do JSON
    }

    @GetMapping(value = "/allCustomers", produces = "application/json")
    public ResponseEntity<String> getAllEmployees() {
        List<Customer> customers = customerRepository.findAll();
        try {
            String json = objectMapper.writeValueAsString(customers);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }


    @GetMapping(value = "/customerCsv")
    public ResponseEntity<byte[]> getCustomerCsv() {
        List<Customer> customers = customerRepository.findAll();

        try {
            // Configura o CSVFormat com as colunas necessárias
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader("AccountNumber", "Name", "FirstName__c", "Phone");

            // Crie um StringWriter para escrever o CSV
            StringWriter stringWriter = new StringWriter();
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            // Escreve os dados dos clientes no CSV
            for (Customer customer : customers) {
                csvPrinter.printRecord(
                        customer.getCustomerNumber(),
                        customer.getContactLastName(),
                        customer.getContactFirstName(),
                        customer.getPhone()
                );
            }

            // Fecha o CSVPrinter
            csvPrinter.close();

            // Converte o CSV em bytes
            byte[] csvBytes = stringWriter.toString().getBytes();

            // Define o nome do arquivo de saída
            fileName = "customer.csv";

            // Caminho completo para o arquivo no diretório src/csv
            String filePath = "src/csv/" + fileName;

            // Salve o arquivo localmente
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(csvBytes);
            }

            // Configura o cabeçalho da resposta para fazer o download do arquivo
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            // Leia o arquivo recém-salvo como bytes
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

            // Configura o Content-Length com o tamanho do arquivo
            headers.setContentLength(fileBytes.length);

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Lida com exceções, se necessário
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new byte[0]); // Corpo vazio em formato de bytes
        }
    }

    public String getFileName() {
        return fileName;
    }
}
