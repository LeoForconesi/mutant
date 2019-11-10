package com.mutant.services;

import com.mutant.domain.DnaSample;
import com.mutant.domain.MutantStats;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface MutantStatsService {

  void storeStats(DnaSample sample) throws ExecutionException, InterruptedException;

  CompletableFuture<MutantStats> obtainStats();

}
