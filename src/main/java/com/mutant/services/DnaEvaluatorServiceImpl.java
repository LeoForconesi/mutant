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

  @Async
  @Override
  public CompletableFuture<Boolean> isMutant(DnaSample sample) {
    int matches = 0;
    char[][] grid = createArray(sample.getDna());
    for (int row = 0; row < ROW_SIZE; row++) {
      for (int col = 0; col < COLUMN_SIZE; col++) {
        if (search2D(grid, row, col, PATTERN_G) ||
                search2D(grid, row, col, PATTERN_A) ||
                search2D(grid, row, col, PATTERN_C) ||
                search2D(grid, row, col, PATTERN_T) ) {
          matches++;
          System.out.println("MATCHES = " + matches);
          System.out.println("pattern found at " + row + ", " + col);
          if(matches >= 4 ) return CompletableFuture.completedFuture(true);
        }
      }
    }
    return CompletableFuture.completedFuture(false);
  }

  // This function searches in all 8-direction from point (row, col) in grid[][]
  private static boolean search2D(char[][] grid, int row, int col, String word) {
    // If first character of word doesn't match with given starting point in grid.
    if (grid[row][col] != word.charAt(0)) {
      return false;
    }

    int len = word.length();

    // Search word in all 8 directions starting from (row,col)
    for (int dir = 0; dir < 8; dir++) {
      // Initialize starting point for current direction
      int k;
      int rd = row + x[dir];
      int cd = col + y[dir];

      // First character is already checked, match remaining characters
      for (k = 1; k < len; k++) {
        // If out of bound break
        if (rd >= ROW_SIZE || rd < 0 || cd >= COLUMN_SIZE || cd < 0)
          break;

        // If not matched, break
        if (grid[rd][cd] != word.charAt(k))
          break;

        // Moving in particular direction
        rd += x[dir];
        cd += y[dir];
      }

      // If all character matched, then value of must be equal to length of word
      if (k == len)
        return true;
    }
    return false;
  }

  private char[][] createArray(String[] dnaList) {
    char[][] mutant;
    COLUMN_SIZE = dnaList[0].length();
    ROW_SIZE = dnaList.length;
    mutant = new char[ROW_SIZE][COLUMN_SIZE];
    for (int i = 0; i < ROW_SIZE; i++) {
      if (COLUMN_SIZE != dnaList[i].length()) {
        throwIllegalArgumentException("Each element in the array must have the same size.");
      }
      char[] row = dnaList[i].toCharArray();
      for (int j = 0; j < row.length; j++) {
        if (row[j] == 'A' || row[j] == 'T' || row[j] == 'C' || row[j] == 'G') {
          mutant[i][j] = row[j];
        } else {
          throwIllegalArgumentException("Invalid argument. It must contains A, T, C or G");
        }
      }
    }
    return mutant;
  }

  private void throwIllegalArgumentException(String message) {
    throw new IllegalArgumentException(message);
  }
}
