package com.oogie.moviesrest.controller;

import com.oogie.moviesrest.CredentialsRepository;
import com.oogie.moviesrest.model.CredentialsEntity;
import com.oogie.moviesrest.model.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CredentialsController {
    private final CredentialsRepository repository;

    public CredentialsController(CredentialsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/credentials")
    List<CredentialsEntity> all() {
        return repository.findAll();
    }

    @PostMapping("/credentials")
    CredentialsEntity newCredentials(@RequestBody CredentialsEntity nuCredentials) {
        return repository.save(nuCredentials);
    }

    @GetMapping("credentials/{id}")
    CredentialsEntity one(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new CredentialsNotFoundException(id));
    }

    @PutMapping("/credentials/{id}")
    CredentialsEntity replaceCredentials(@RequestBody CredentialsEntity nuCredentials, @PathVariable Integer id) {
        return repository.findById(id).map(credentials -> {
            credentials.setUsername(nuCredentials.getUsername());
            credentials.setPassword(nuCredentials.getPassword());
            credentials.setAffiliation(nuCredentials.getAffiliation());
            return repository.save(credentials);
        }).orElseGet(() -> {
            nuCredentials.setId(id);
            return repository.save(nuCredentials);
        });
    }

    @DeleteMapping("/credentials/{id}")
    ErrorResponse deleteCredentials(@PathVariable Integer id) {
        try {
            repository.deleteById(id);
            return new ErrorResponse();
        } catch (Exception e) {
            String s = formatErrorResponse(e.getMessage(), CredentialsEntity.class);
            return new ErrorResponse(-1, s);
        }
    }

    private String formatErrorResponse(String origMessage, Class c) {
        String searchStr = c.getName();
        int startIndex = origMessage.indexOf(searchStr);
        int endIndex = startIndex + searchStr.length();
        String s1 = origMessage.substring(0, startIndex);
        String s2 = origMessage.substring(endIndex + 1);
        StringBuilder sb = new StringBuilder(s1);
        sb.append(s2);
        return sb.toString();
    }
}
