package com.mutant.services;

import com.mutant.domain.DnaSample;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
class DnaEvaluatorServiceImpl implements DnaEvaluatorService {
  private static final String PATTERN_A = "AAAA";
  private static final String PATTERN_G = "GGGG";
  private static final String PATTERN_C = "CCCC";
  private static final String PATTERN_T = "TTTT";
  // Rows and columns in given grid
  private static int ROW_SIZE, COLUMN_SIZE;

  // For searching in all 8 direction
  private static final int[] x = {-1, -1, -1, 0, 0, 1, 1, 1};
  private static final int[] y = {-1, 0, 1, -1, 1, -1, 0, 1};

  @Override
  @Async("taskExecutor")
  public CompletableFuture<Boolean> isMutant(DnaSample sample) throws IllegalArgumentException {
    int matches = 0;
    char[][] grid = createArray(sample.getDna());
    for (int row = 0; row < ROW_SIZE; row++) {
      for (int col = 0; col < COLUMN_SIZE; col++) {
        if (searchPattern(grid, row, col, PATTERN_G) ||
            searchPattern(grid, row, col, PATTERN_A) ||
            searchPattern(grid, row, col, PATTERN_C) ||
            searchPattern(grid, row, col, PATTERN_T)) {
          matches++;
          if(matches >= 4 ) {
            return CompletableFuture.completedFuture(true);
          }
        }
      }
    }
    return CompletableFuture.completedFuture(false);
  }

  /**
   *
   * @param grid char[][] with the grid of characters received from the controller
   * @param row int, it's the row position index in the grid to search in its neighbor cells
   * @param col int, it's the column position index in the grid to search in its neighbor cells
   * @param dnaPattern String, represents the determined dnaPattern to search in the grid
   * @return Boolean, If dnaPatter String is found in the gridit returns true.
   */
  private static Boolean searchPattern(char[][] grid, int row, int col, String dnaPattern) {
    if (grid[row][col] != dnaPattern.charAt(0)) {
      return false;
    }

    int wordLength = dnaPattern.length();

    for (int dir = 0; dir < 8; dir++) {
      int wordIndex;
      int rowNeighborDirection = row + x[dir];
      int columnNeighborDirection = col + y[dir];

      // First character is already checked, match remaining characters
      for (wordIndex = 1; wordIndex < wordLength; wordIndex++) {
        // If out of bound break
        if (rowNeighborDirection >= ROW_SIZE || rowNeighborDirection < 0 || columnNeighborDirection >= COLUMN_SIZE || columnNeighborDirection < 0)
          break;

        // If not matched, break
        if (grid[rowNeighborDirection][columnNeighborDirection] != dnaPattern.charAt(wordIndex))
          break;

        // Moving in particular direction
        rowNeighborDirection += x[dir];
        columnNeighborDirection += y[dir];
      }

      // If all character matched, then value of must be equal to length of dnaPattern
      if (wordIndex == wordLength) {
        return true;
      }
    }
    return false;
  }

  @Override
  public char[][] createArray(String[] dna) throws IllegalArgumentException {
    COLUMN_SIZE = dna[0].length();
    ROW_SIZE = dna.length;
    char[][] dnaGrid = new char[ROW_SIZE][COLUMN_SIZE];
    for (int i = 0; i < ROW_SIZE; i++) {
      if (COLUMN_SIZE != dna[i].length()) {
        throw new IllegalArgumentException("Each element in the array must have the same size.");
      }
      char[] row = dna[i].toCharArray();
      for (int j = 0; j < row.length; j++) {
        if (row[j] == 'A' || row[j] == 'T' || row[j] == 'C' || row[j] == 'G') {
          dnaGrid[i][j] = row[j];
        } else {
          throw new IllegalArgumentException("Invalid argument. It must contains A, T, C or G");
        }
      }
    }
    return dnaGrid;
  }
}
