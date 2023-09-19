package com.example.demointegracaomysqlsalesforce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "productCode")
    private String productCode;

    @Column(name = "productName", length = 100)
    private String productName;

    @Column(name = "productLine", length = 50)
    private String productLine;

    @Column(name = "productScale", length = 10)
    private String productScale;

    @Column(name = "productVendor", length = 50)
    private String productVendor;

    @Column(name = "productDescription", length = 500)
    private String productDescription;

    @Column(name = "quantityInStock")
    private int quantityInStock;

    // Construtores, getters e setters (não mostrados aqui por brevidade)


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductScale() {
        return productScale;
    }

    public void setProductScale(String productScale) {
        this.productScale = productScale;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}


