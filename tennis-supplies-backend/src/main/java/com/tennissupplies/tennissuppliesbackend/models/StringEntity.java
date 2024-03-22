package com.tennissupplies.tennissuppliesbackend.models;
import jakarta.persistence.*;

@Entity
public class StringEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StringEntity() {

    }
    public StringEntity(String name) {
        this.name = name;
    }
}