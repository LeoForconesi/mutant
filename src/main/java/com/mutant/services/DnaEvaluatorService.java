package com.mutant.services;

import com.mutant.domain.DnaSample;

import java.util.concurrent.CompletableFuture;

/**
 * @author - Leonardo A. Forconesi
 */
public interface DnaEvaluatorService {
  /**
   *
   * @param sample sample that contains subject's dna
   * @return CompletableFuture with a boolean, indicating if the sample corresponds to a mutant or not
   * @throws IllegalArgumentException if the sample is not valid
   */
  CompletableFuture<Boolean> isMutant(DnaSample sample) throws IllegalArgumentException;

  /**
   *
   * @param dnaList String of the dna
   * @return returns a two dimensional array of chars
   * @throws IllegalArgumentException if the dna is invalid
   */
  char[][] createArray(String[] dnaList) throws IllegalArgumentException;
}
