package com.example.demo.model.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.repository.TestRepository;
import com.example.demo.model.domain.TestDB;

@Service
public class Testservice { // public 클래스는 이것 하나뿐이어야 합니다.

    @Autowired
    private TestRepository testRepository;

    public TestDB findByName(String name) {
        return testRepository.findByName(name);
    }

    public TestDB findById(long id) {
        return testRepository.findById(id);
    }
}