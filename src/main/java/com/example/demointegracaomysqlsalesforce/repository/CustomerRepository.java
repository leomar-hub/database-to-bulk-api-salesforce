package com.example.demointegracaomysqlsalesforce.repository;

import com.example.demointegracaomysqlsalesforce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c " +
            "WHERE (:customerNumber is null OR c.customerNumber = :customerNumber) " +
            "AND (:customerName is null OR c.customerName = :customerName) " +
            "AND (:contactLastName is null OR c.contactLastName = :contactLastName) " +
            "AND (:contactFirstName is null OR c.contactFirstName = :contactFirstName) " +
            "AND (:phone is null OR c.phone = :phone) " +
            "AND (:addressLine1 is null OR c.addressLine1 = :addressLine1) " +
            "AND (:addressLine2 is null OR c.addressLine2 = :addressLine2) " +
            "AND (:city is null OR c.city = :city) " +
            "AND (:state is null OR c.state = :state) " +
            "AND (:postalCode is null OR c.postalCode = :postalCode) " +
            "AND (:country is null OR c.country = :country) " +
            "AND (:salesRepEmployeeNumber is null OR c.salesRepEmployeeNumber = :salesRepEmployeeNumber) " +
            "AND (:creditLimit is null OR c.creditLimit = :creditLimit)")
    List<Customer> findCustomersByFilters(@Param("customerNumber") Long customerNumber,
                                          @Param("customerName") String customerName,
                                          @Param("contactLastName") String contactLastName,
                                          @Param("contactFirstName") String contactFirstName,
                                          @Param("phone") String phone,
                                          @Param("addressLine1") String addressLine1,
                                          @Param("addressLine2") String addressLine2,
                                          @Param("city") String city,
                                          @Param("state") String state,
                                          @Param("postalCode") String postalCode,
                                          @Param("country") String country,
                                          @Param("salesRepEmployeeNumber") Long salesRepEmployeeNumber,
                                          @Param("creditLimit") Double creditLimit);
}



