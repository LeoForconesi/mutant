package com.mutant.domain;

import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class DnaSample {
  @Id private String dnaId;
  private String[] dna;
  private Boolean isMutant;

  public DnaSample(String[] dna, Boolean isMutant) {
    this.dna = dna;
    this.isMutant = isMutant;
  }

  public DnaSample(String[] dna) {
    this.dna = dna;
  }

  public DnaSample() {
  }

  public String[] getDna() {
    return dna;
  }

  public void setDna(String[] dna) {
    this.dna = dna;
  }

  public Boolean getMutant() {
    return isMutant;
  }

  public void setMutant(Boolean mutant) {
    isMutant = mutant;
  }

  @Override
  public String toString() {
    return "DnaSample{" +
            "dnaId='" + dnaId + '\'' +
            ", dna=" + Arrays.toString(dna) +
            ", isMutant=" + isMutant +
            '}';
  }
}
