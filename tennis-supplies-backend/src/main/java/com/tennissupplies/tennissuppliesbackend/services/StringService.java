package com.tennissupplies.tennissuppliesbackend.services;

import com.tennissupplies.tennissuppliesbackend.models.StringEntity;
import com.tennissupplies.tennissuppliesbackend.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StringService {

    @Autowired
    private StringRepository stringRepository;

    public List<StringEntity> list() {
        return stringRepository.findAll();
    }
    public void setString(String newString) {
        StringEntity stringEntity = new StringEntity(newString);
        stringRepository.save(stringEntity);
    }
}
