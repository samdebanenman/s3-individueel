package com.tennissupplies.tennissuppliesbackend.controller;
import com.tennissupplies.tennissuppliesbackend.models.StringEntity;
import com.tennissupplies.tennissuppliesbackend.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class StringController {

    private final StringService stringService;

    @Autowired
    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @PostMapping("/addString")
    public CompletableFuture<ResponseEntity<Void>> addString(@RequestBody String newString) {
        return stringService.setString(newString)
                .thenApply(aVoid -> new ResponseEntity<Void>(HttpStatus.OK))
                .exceptionally(e -> {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add string to the database", e);
                });
    }

    @GetMapping("/getStrings")
    public CompletableFuture<ResponseEntity<List<StringEntity>>> getAllStrings() {
        return stringService.list()
                .thenApply(strings -> new ResponseEntity<>(strings, HttpStatus.OK))
                .exceptionally(e -> {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve strings from the database", e);
                });
    }
}
