package com.mutant.controllers;

import com.mutant.domain.DnaSample;
import com.mutant.services.DnaEvaluatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@RestController
public class MutantController {

  private static final Logger log = LoggerFactory.getLogger(MutantController.class);

  @Autowired
  private DnaEvaluatorService dnaEvaluatorService;

  /**
   *
   * @param dnaSample String Array containing Dna secuence to be determined wheter it belongs to a mutant or not
   * @return  200 if it's a mutant, or a 403 if not
   */
  @RequestMapping(value="/mutant", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> analizeMutantCandidate(@RequestBody DnaSample dnaSample) {
    log.info("Checking if the following sample belongs to a mutant: {}" + Arrays.toString(dnaSample.getDna()));
    Boolean isMutant = null;
    try {
      isMutant = dnaEvaluatorService.isMutant(dnaSample).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    if (isMutant == Boolean.TRUE) {
      return new ResponseEntity<>("Hello Mutant!", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("NO ES MUTANTE!", HttpStatus.FORBIDDEN);
    }
  }
}
