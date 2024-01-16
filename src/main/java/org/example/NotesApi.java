package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication // Annotations or decorators
@EnableMongoAuditing
@EnableMongoRepositories
public class NotesApi {
    public static void main(String[] args) {
        SpringApplication.run(NotesApi.class, args);
    }
}