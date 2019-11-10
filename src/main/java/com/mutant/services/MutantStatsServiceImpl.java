package com.mutant.services;

import com.mutant.db.MutantRepository;
import com.mutant.db.MutantStatsRepository;
import com.mutant.domain.DnaSample;
import com.mutant.domain.MutantStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MutantStatsServiceImpl implements MutantStatsService {
  private static final Logger log = LoggerFactory.getLogger(MutantStatsServiceImpl.class);

  @Autowired
  MutantRepository mutantRepository;

  @Autowired
  MutantStatsRepository mutantStatsRepository;

  @Override
  public void storeStats(DnaSample sample) throws ExecutionException, InterruptedException {
    if (sample != null && sample.getMutant() != null) {
      MutantStats stats = obtainStats().get();
      if (sample.getMutant()) {
        stats.add_mutant();
      } else {
        stats.add_human();
      }
      mutantStatsRepository.save(stats);
    } else {
      log.error("Error in storeStats Service, DnaSample is Undetermined");
      throw new IllegalArgumentException("Couldn't save stat of an undetermined sample.");
    }
  }

  @Async
  @Override
  public CompletableFuture<MutantStats> obtainStats() {
    List<DnaSample> registry = mutantRepository.findAll();
    Map<Boolean, Long> map = registry.stream().collect(groupingBy( DnaSample::getMutant, counting()));
    MutantStats stats =  new MutantStats(map.get(true), map.get(false));
    return CompletableFuture.completedFuture(stats);
  }
}
