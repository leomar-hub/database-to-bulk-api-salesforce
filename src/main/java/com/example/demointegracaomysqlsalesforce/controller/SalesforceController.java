package com.example.demointegracaomysqlsalesforce.controller;

import com.example.demointegracaomysqlsalesforce.integrationSalesforce.SalesforceBulkApiClient;
import com.example.demointegracaomysqlsalesforce.model.Customer;
import com.example.demointegracaomysqlsalesforce.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SalesforceController {

    private final SalesforceBulkApiClient salesforceBulkApiClient;
    private final CustomerRepository customerRepository;
    private final CustomerController customerController;

    @Autowired
    public SalesforceController(SalesforceBulkApiClient salesforceBulkApiClient, CustomerRepository customerRepository, CustomerController customerController) {
        this.salesforceBulkApiClient = salesforceBulkApiClient;
        this.customerRepository = customerRepository;
        this.customerController = customerController;
    }

    @PostMapping("/executeImport")
    public ResponseEntity<String> executeImport() {
        try {
            // Chama o método customerCsv da classe CustomerController para gerar o arquivo CSV
            customerController.getCustomerCsv();

            String accessToken = salesforceBulkApiClient.getAccessToken();
            if (accessToken != null) {
                String jobResponse = salesforceBulkApiClient.createJob(accessToken);

                List<Customer> customers = customerRepository.findAll();

                // Obtenha o conteúdo do arquivo CSV a partir do diretório
                Path filePath = Paths.get("src/csv/customer.csv");
                String directory = "/src/csv/";
                //Path filePath = Paths.get(directory).resolve(customerController.getFileName());
                String csvData = Files.readString(filePath);

                // Chama o método insertData passando o conteúdo do arquivo CSV
                salesforceBulkApiClient.insertData(accessToken, jobResponse, csvData);

                // Feche o job no Salesforce
                salesforceBulkApiClient.closeJob(accessToken, jobResponse);
                String closedResponse = salesforceBulkApiClient.getClosedResponse();

                // Crie um objeto JSON com a mensagem e jobResponse
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Habilita a formatação bonita
                Map<String, Object> jsonResponse = new LinkedHashMap<>();
                jsonResponse.put("message", "Sucesso ao inserir dados no Salesforce.");
                jsonResponse.put("body", new ObjectMapper().readValue(closedResponse, Object.class));

                Files.deleteIfExists(filePath);

                // Retorna a resposta JSON formatada com indentação
                return ResponseEntity.ok(objectMapper.writeValueAsString(jsonResponse));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao obter access token");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao executar importação dos dados: " + e.getMessage());
        }
    }
}
