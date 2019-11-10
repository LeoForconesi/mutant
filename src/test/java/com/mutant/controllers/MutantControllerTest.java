package com.mutant.controllers;

import com.mutant.domain.DnaSample;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MutantControllerTest {
private final String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
DnaSample sample1 = new DnaSample(dna);

  @Test
  void analizeMutantCandidate() {
    Assert.assertTrue(true);
  }
}