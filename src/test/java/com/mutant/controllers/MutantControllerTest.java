package com.mutant.controllers;

import com.mutant.domain.DnaSample;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantControllerTest {
String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
DnaSample sample1 = new DnaSample(dna);

  @BeforeEach
  void setUp() {
  }

  @Test
  void analizeMutantCandidate() {
    Assert.assertTrue(true);
  }
}