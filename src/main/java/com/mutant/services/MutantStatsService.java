package com.mutant.services;

import com.mutant.domain.DnaSample;
import com.mutant.domain.MutantStats;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author - Leonardo A. Forconesi
 */
public interface MutantStatsService {
  /**
   *
   * @param sample DnaSample to store in db
   * @throws ExecutionException
   * @throws InterruptedException
   */
  void storeStats(DnaSample sample) throws ExecutionException, InterruptedException;

  /**
   *
    * @return returns stats of the entire totals of mutants or humans
   */
  CompletableFuture<MutantStats> obtainStats();

}
