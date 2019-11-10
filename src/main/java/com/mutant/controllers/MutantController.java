package com.mutant.controllers;

import com.mutant.db.MutantRepository;
import com.mutant.domain.DnaSample;
import com.mutant.domain.MutantStats;
import com.mutant.services.DnaEvaluatorService;
import com.mutant.services.MutantStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@RestController
class MutantController {

  private static final Logger log = LoggerFactory.getLogger(MutantController.class);

  @Autowired
  private final DnaEvaluatorService dnaEvaluatorService;

  @Autowired
  private final MutantStatsService mutantStatsService;

  @Autowired
  private MutantRepository mutantRepository;

  public MutantController(DnaEvaluatorService dnaEvaluatorService, MutantStatsService mutantStatsService, MutantRepository mutantRepository) {
    this.dnaEvaluatorService = dnaEvaluatorService;
    this.mutantStatsService = mutantStatsService;
    this.mutantRepository = mutantRepository;
  }

  /**
   *
   * @param dnaSample String Array containing Dna sequence to be determined whether it belongs to a mutant or not
   * @return  200 if it's a mutant, or a 403 if not
   */
  @PostMapping(path ="/mutant", consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> analyzeMutantCandidate(@RequestBody @Valid DnaSample dnaSample) {
    log.info("Checking if the following sample belongs to a mutant: {}", Arrays.toString(dnaSample.getDna()));
    try {
      Boolean isMutant = dnaEvaluatorService.isMutant(dnaSample).get();
      DnaSample result = new DnaSample(dnaSample.getDna(), isMutant);
      mutantRepository.save(result);
      mutantStatsService.storeStats(result);
      return isMutant ? new ResponseEntity<>("Mutant", HttpStatus.OK) :
              new ResponseEntity<>("Human", HttpStatus.FORBIDDEN);
    } catch(IllegalArgumentException e) {
      log.error("Malformed Grid ", e);
      return new ResponseEntity<>("Malformed Grid", HttpStatus.BAD_REQUEST);
    } catch( Exception e) {
      log.error("Internal Error ", e);
      return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   *
   * @return ADN: {“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
   */
  @GetMapping(path ="stats")
  public ResponseEntity<MutantStats> resultStatistics() {
    try {
      CompletableFuture<MutantStats> mutantStats = mutantStatsService.obtainStats();
      return new ResponseEntity<>(mutantStats.get(), HttpStatus.OK);
    } catch(Exception e) {
      log.error("Error trying to get mutants stats. ", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @SuppressWarnings("SameReturnValue")
  @GetMapping(path="/serviceStatus")
  public String isServerUp() {
    log.info("Checking Api status");
    return "Api is Up";
  }
}
