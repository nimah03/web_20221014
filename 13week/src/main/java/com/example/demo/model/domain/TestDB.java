package com.example.demo.model.domain;

import javax.persistence.Column; // Spring Boot 2.x에서는 javax.persistence 사용
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "testdb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

}