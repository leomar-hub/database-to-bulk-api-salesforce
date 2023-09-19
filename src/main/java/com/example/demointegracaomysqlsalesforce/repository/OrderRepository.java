package com.example.demointegracaomysqlsalesforce.repository;

import com.example.demointegracaomysqlsalesforce.model.Employee;
import com.example.demointegracaomysqlsalesforce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}


