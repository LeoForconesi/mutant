package com.mutant.db;

import com.mutant.domain.DnaSample;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MutantRepository extends MongoRepository<DnaSample, String> {
}
