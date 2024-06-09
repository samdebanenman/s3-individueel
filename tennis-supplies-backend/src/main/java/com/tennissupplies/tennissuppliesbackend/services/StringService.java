package com.tennissupplies.tennissuppliesbackend.services;

import com.tennissupplies.tennissuppliesbackend.models.StringEntity;
import com.tennissupplies.tennissuppliesbackend.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StringService {
    private final StringRepository stringRepository;

    // Constructor injection
    @Autowired
    public StringService(StringRepository stringRepository) {
        this.stringRepository = stringRepository;
    }

    @Async
    public CompletableFuture<List<StringEntity>> list() {

        return CompletableFuture.completedFuture(stringRepository.findAll());
    }

    @Async
    public CompletableFuture<Void> setString(String newString) {
        return CompletableFuture.runAsync(() -> {
            StringEntity stringEntity = new StringEntity(newString);
            stringRepository.save(stringEntity);
        });
    }

}
