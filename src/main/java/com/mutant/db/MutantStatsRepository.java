package com.mutant.db;

import com.mutant.domain.MutantStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MutantStatsRepository extends MongoRepository<MutantStats, String> {
}
