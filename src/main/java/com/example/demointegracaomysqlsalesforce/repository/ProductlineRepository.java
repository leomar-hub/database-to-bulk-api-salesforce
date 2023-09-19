package com.example.demointegracaomysqlsalesforce.repository;

import com.example.demointegracaomysqlsalesforce.model.Employee;
import com.example.demointegracaomysqlsalesforce.model.Productline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductlineRepository extends JpaRepository<Productline, Long> {
}


