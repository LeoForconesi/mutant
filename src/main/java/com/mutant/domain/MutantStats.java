package com.mutant.domain;

import org.springframework.data.annotation.Id;

/**
 * @author - Leonardo A. Forconesi
 */
public class MutantStats {
  @Id private String id = "statsTable";
  private Long count_mutant_dna = 0L;
  private Long count_human_dna = 0L;
  private Float ratio;

  public MutantStats() {
  }

  public MutantStats(Long count_mutant_dna, Long count_human_dna) {
    this.count_mutant_dna = count_mutant_dna;
    this.count_human_dna = count_human_dna;
    this.ratio = calculateRatio();
  }

  @Override
  public String toString() {
    return "[mutants: " + count_mutant_dna + ", humans: " + count_human_dna + ", ratio: " + ratio + "]";
  }

  public Long getCount_mutant_dna() {
    return count_mutant_dna;
  }

  public void add_mutant() {
    ++this.count_mutant_dna;
    this.ratio = calculateRatio();
  }

  public Long getCount_human_dna() {
    return count_human_dna;
  }

  public void add_human() {
    ++this.count_human_dna;
    this.ratio = calculateRatio();
  }

  public Float getRatio() {
    return ratio;
  }

  private float calculateRatio() {
    return this.count_human_dna != null &&
           this.count_mutant_dna != null &&
           this.count_human_dna > 0 ? (float)this.count_mutant_dna / this.count_human_dna : 0;
  }
}
