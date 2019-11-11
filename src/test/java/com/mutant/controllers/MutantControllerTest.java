package com.mutant.controllers;

import com.mutant.MutantApplication;
import com.mutant.db.MutantRepository;
import com.mutant.domain.DnaSample;
import com.mutant.domain.MutantStats;
import com.mutant.services.DnaEvaluatorService;
import com.mutant.services.MutantStatsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutantApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantControllerTest {

  @Autowired
  DnaEvaluatorService dnaEvaluatorService;

  @Autowired
  MutantStatsService mutantStatsService;

  @Autowired
  private MutantRepository mutantRepository;

  private MutantController mutantController;

  private static final String[] HUMAN_DNA = {"TTGCGA","CAGTGC","TTATGT","AGAATG","CCCCTA","TCACTA"};
  private static final String[] MUTANT_DNA = {"TTTTTA","CAGTGC","TTATGT","AGAAGG","CACCTA","TCACTG"};
  private static final String[] MALFORMED_DNA_1 = {"xxxxxx","xxxxxx","xxxxxx","xxxxxx","xxxxxx","xxxxxx"};
  private static final String[] MALFORMED_DNA_2 = {"TTTTA","CTGC","","zGAAGG","CACCTA","TCACTG"};


  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    mutantController = new MutantController(dnaEvaluatorService, mutantStatsService, mutantRepository);
  }

  @Test
  public void mutantControllerTest() {
    DnaSample humanSample = new DnaSample(HUMAN_DNA);
    ResponseEntity responseEntity = mutantController.analyzeMutantCandidate(humanSample);
    Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    assertThat(mutantRepository.findAll().get(0))
            .extracting("isMutant").containsOnly(false);

    DnaSample mutantSample = new DnaSample(MUTANT_DNA);
    responseEntity = mutantController.analyzeMutantCandidate(mutantSample);
    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertThat(mutantRepository.findAll().get(1))
            .extracting("isMutant").containsOnly(true);

    DnaSample badSample1 = new DnaSample(MALFORMED_DNA_1);
    responseEntity = mutantController.analyzeMutantCandidate(badSample1);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    DnaSample badSample2 = new DnaSample(MALFORMED_DNA_2);
    responseEntity = mutantController.analyzeMutantCandidate(badSample2);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    assertThat(mutantRepository.findAll().size()).isEqualTo(2);

    mutantRepository.deleteAll();
  }

  @Test
  public void statsControllerTest() throws ExecutionException, InterruptedException {
    DnaSample humanSample = new DnaSample(HUMAN_DNA);
    DnaSample mutantSample = new DnaSample(MUTANT_DNA);

    for(int i = 0; i < 10; i++) {
      mutantController.analyzeMutantCandidate(humanSample);
    }
    for(int i = 0; i < 4; i++) {
      mutantController.analyzeMutantCandidate(mutantSample);
    }

    ResponseEntity responseEntity = mutantController.resultStatistics();
    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    CompletableFuture<MutantStats> stats = mutantStatsService.obtainStats();

    Assert.assertEquals((long) stats.get().getCount_human_dna(), 10);
    Assert.assertEquals((long)stats.get().getCount_mutant_dna(), 4);
    Assert.assertEquals(0, Float.compare(stats.get().getRatio(), (float) 0.4));

    mutantRepository.deleteAll();
  }
}
