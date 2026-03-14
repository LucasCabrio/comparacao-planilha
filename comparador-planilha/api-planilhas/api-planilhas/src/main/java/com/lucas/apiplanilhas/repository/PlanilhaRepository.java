package com.lucas.apiplanilhas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lucas.apiplanilhas.model.Planilha;

public interface PlanilhaRepository extends MongoRepository<Planilha, String> {
}
