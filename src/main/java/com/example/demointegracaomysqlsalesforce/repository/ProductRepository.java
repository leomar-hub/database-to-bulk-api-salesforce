package com.example.demointegracaomysqlsalesforce.repository;

import com.example.demointegracaomysqlsalesforce.model.Employee;
import com.example.demointegracaomysqlsalesforce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}


