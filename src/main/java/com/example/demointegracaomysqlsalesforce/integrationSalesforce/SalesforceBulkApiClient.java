package com.example.demointegracaomysqlsalesforce.integrationSalesforce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Map;

@Component
@SpringBootApplication
public class SalesforceBulkApiClient {

    private static final String SALESFORCE_LOGIN_URL = "https://login.salesforce.com/services/oauth2/token";
    private static final String SALESFORCE_API_ENDPOINT = "https://mysalesforce-dev-ed.my.salesforce.com/services/data/v57.0/jobs/ingest";
    private static final String CLIENT_ID = "3MVG9JEx.**********89QdxTc9u0ug1KiCMfb1zq4***********.7CQnmqU3P14lyRILkhseg5SRQlOYMlhPqZw";
    private static final String CLIENT_SECRET = "37276876542DF***************F036EBD2200E4B72A9A270AF83856358F2033";
    private static final String USERNAME = "s**********r@hotmail.com";
    private static final String PASSWORD = "**********";

    private String accessToken;
    private String jobResponse;
    private String closedResponse;
    private String jobId;
    private String batchId;

    //get token
    public String getAccessToken() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Parâmetros para a solicitação OAuth
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("username", USERNAME);
        requestBody.add("password", PASSWORD);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(SALESFORCE_LOGIN_URL, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
            Map<String, Object> responseBody = objectMapper.readValue(responseEntity.getBody(), mapType);
            accessToken = (String) responseBody.get("access_token");

            System.out.println("###--Get token---###");
            System.out.println("Token: Bearer " + accessToken);

            return accessToken;

        } else {
            System.err.println("Failed to obtain access token. Status code: " + responseEntity.getStatusCode());
            return null;
        }
    }

    //create job
    public String createJob(String accessToken) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jobData = "{"
                + "\"operation\":\"insert\","
                + "\"object\":\"Account\","
                + "\"contentType\":\"CSV\","
                + "\"lineEnding\":\"CRLF\""
                + "}";

        HttpEntity<String> requestEntity = new HttpEntity<>(jobData, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(SALESFORCE_API_ENDPOINT, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            jobResponse = responseEntity.getBody();

            System.out.println("###--Create Job---###");
            System.out.println(jobResponse);

            return jobResponse;

        } else {
            System.err.println("Failed to create job. Status code: " + responseEntity.getStatusCode());
            return null;
        }
    }

    //insert data
    public String insertData(String accessToken, String jobResponse, String csvData) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.valueOf("text/csv"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(jobResponse);
        jobId = jsonResponse.get("id").asText();

        // Monta o URL completo para enviar os dados para o Salesforce
        String createJobUrl = SALESFORCE_API_ENDPOINT + "/" + jobId + "/batches";

        HttpEntity<String> requestEntity = new HttpEntity<>(csvData, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(createJobUrl, HttpMethod.PUT, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            batchId = responseEntity.getBody();

            System.out.println("###--Insert data---###");
            return batchId;

        } else {
            System.err.println("Failed to insert data. Status code: " + responseEntity.getStatusCode());
        }
        return createJobUrl;
    }

    //closed job
    public String closeJob(String accessToken, String jobResponse) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Extrai o Job ID da resposta
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(jobResponse);
        jobId = jsonResponse.get("id").asText();

        // Monta a URL para fechar o job
        String closedJobUrl = SALESFORCE_API_ENDPOINT + "/" + jobId;

        HttpPatch httpPatch = new HttpPatch(closedJobUrl);

        // Configure os cabeçalhos
        httpPatch.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        httpPatch.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

        // Crie um objeto JSON para indicar que o job deve ser fechado
        String closeData = "{\"state\":\"UploadComplete\"}";
        httpPatch.setEntity(new StringEntity(closeData, ContentType.APPLICATION_JSON));

        // Execute a solicitação PATCH com o HttpClient
        CloseableHttpResponse response = httpClient.execute(httpPatch);
        int statusCode = response.getStatusLine().getStatusCode();

        closedResponse = EntityUtils.toString(response.getEntity());

        if (statusCode == 200) {
            System.out.println("###--Closed job---###");
            System.out.println(closedResponse);
            httpClient.close();

            return closedResponse;

        } else {
            System.err.println("Failed to close job. Status code: " + statusCode);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.err.println("Response body: " + responseBody);
        }

        httpClient.close();
        return null;
    }

    public String getClosedResponse() {
        return closedResponse;
    }

}

