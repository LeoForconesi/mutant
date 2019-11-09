package com.mutant.db;

import com.mutant.domain.Greeting;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GreetingRepository extends MongoRepository<Greeting, String> {
// add here custom queries
}
