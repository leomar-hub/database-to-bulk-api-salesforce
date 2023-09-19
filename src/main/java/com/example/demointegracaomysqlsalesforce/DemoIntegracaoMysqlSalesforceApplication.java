package com.example.demointegracaomysqlsalesforce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.example")
@EntityScan(basePackages = {"com.example.demointegracaomysqlsalesforce.model"})
@EnableJpaRepositories(basePackages = {"com.example.demointegracaomysqlsalesforce.repository"})
@EnableTransactionManagement
public class DemoIntegracaoMysqlSalesforceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoIntegracaoMysqlSalesforceApplication.class, args);
        //System.out.println(new BCryptPasswordEncoder().encode("digibeetest123")); //Digite a senha escolhida para gerar o padrão
    }
    /*Caso a tabela de usuario estaja vazia: para obter uma senha no padrão do JWT adicione um brake point na linha dos sysout acima
    e execute em modo debug, quando a execução chegar brake point precine F8 que a senha será gerada e
    exibida no console. Este é o padrão de senha que deve ser cadastrado no banco*/

    /*
    Usuário master do banco
    "login": "leomar",
    "senha": "test123"
    */

}

