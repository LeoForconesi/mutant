package com.mutant.services;

import com.mutant.domain.DnaSample;

import java.util.concurrent.CompletableFuture;

public interface DnaEvaluatorService {

  CompletableFuture<Boolean> isMutant(DnaSample sample) throws IllegalArgumentException;

  char[][] createArray(String[] dnaList) throws IllegalArgumentException;
}
