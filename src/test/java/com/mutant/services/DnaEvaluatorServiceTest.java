package com.mutant.services;

import com.mutant.MutantApplication;
import com.mutant.domain.DnaSample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MutantApplication.class}, loader = AnnotationConfigContextLoader.class)
public class DnaEvaluatorServiceTest {

  private final String[] DNA_MUTANT = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
  private final String[] DNA_NOT_MUTANT = {"ATGCCA","CTGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
  private final String[] DNA_BAD_CHAR = {"XTGCCA","CTGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};


  @Autowired
  private DnaEvaluatorService dnaEvaluatorService;

  @Test
  public void testIsMutant() throws InterruptedException, ExecutionException {
    DnaSample mutantSample = new DnaSample(DNA_MUTANT);
    CompletableFuture<Boolean> isMutant = dnaEvaluatorService.isMutant(mutantSample);
    Assert.assertTrue(isMutant.get());
  }

  @Test
  public void testIsNotMutant() throws InterruptedException, ExecutionException {
    DnaSample mutantSample = new DnaSample(DNA_NOT_MUTANT);
    CompletableFuture<Boolean> isMutant = dnaEvaluatorService.isMutant(mutantSample);
    Assert.assertFalse(isMutant.get());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharInDna() throws IllegalArgumentException {
    DnaSample mutantSample = new DnaSample(DNA_BAD_CHAR);
    dnaEvaluatorService.createArray(mutantSample.getDna());
  }
}
