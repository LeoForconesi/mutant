package com.mutant.services;

import com.mutant.MutantApplication;
import com.mutant.domain.DnaSample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MutantApplication.class}, loader = AnnotationConfigContextLoader.class)
public class DnaEvaluatorServiceTest {

  private String[] DNA_VALID = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

  @Autowired
  private DnaEvaluatorService dnaEvaluatorService;

  @Test
  public void testIsMutant() throws InterruptedException, ExecutionException {
    DnaSample mutantSample = new DnaSample();
    mutantSample.setDna(DNA_VALID);
    CompletableFuture<Boolean> isMutant = dnaEvaluatorService.isMutant(mutantSample);
    Assert.assertTrue(isMutant.get());
  }
}
