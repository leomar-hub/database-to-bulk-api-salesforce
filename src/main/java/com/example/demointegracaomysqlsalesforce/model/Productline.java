package com.example.demointegracaomysqlsalesforce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Productline {

    @Id
    @Column(length = 50)
    private String productLine;

    @Column(columnDefinition = "TEXT")
    private String textDescription;

    @Column(columnDefinition = "TEXT")
    private String htmlDescription;

    @Column(length = 100)
    private String image;

    // Construtores, getters e setters podem ser adicionados aqui

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

