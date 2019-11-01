package com.mutant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MutantApplication {

  public static void main(String[] args) {
    SpringApplication.run(MutantApplication.class, args);
  }
}
