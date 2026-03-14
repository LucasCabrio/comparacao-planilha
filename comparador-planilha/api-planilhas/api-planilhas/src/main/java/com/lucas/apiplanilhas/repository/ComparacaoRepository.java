package com.lucas.apiplanilhas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lucas.apiplanilhas.model.ComparacaoPlanilha;

public interface ComparacaoRepository extends MongoRepository<ComparacaoPlanilha, String> {
}
