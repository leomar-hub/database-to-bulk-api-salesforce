package com.example.demointegracaomysqlsalesforce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootTest
class DemoIntegracaoMysqlSalesforceApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://0.tcp.sa.ngrok.io:10105/classicmodels?useSSL=false";
        String user = "testdigibee";
        String password = "digibee123";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Conexão bem-sucedida!");
            connection.close();
        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }
    }

}
