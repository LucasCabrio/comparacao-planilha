package com.lucas.apiplanilhas.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.lucas.apiplanilhas.model.ImportUser;

public interface ImportUserRepository extends MongoRepository <ImportUser, String> {

}
