package com.mutant.services;

import com.mutant.domain.DnaSample;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DnaEvaluatorServiceImpl implements DnaEvaluatorService {
  // Rows and columns in given grid
  private static int R, C;

  // For searching in all 8 direction
  private static int[] x = {-1, -1, -1, 0, 0, 1, 1, 1};
  private static int[] y = {-1, 0, 1, -1, 1, -1, 0, 1};

  @Async
  @Override
  public CompletableFuture<Boolean> isMutant(DnaSample sample) {
    char[][] grid = createArray(sample.getDna());
    search2D(grid);
//    R = 6;
//    C = 6;
//    for (int row = 0; row < R; row++) {
//      for (int col = 0; col < C; col++) {
//        if (search2D(grid, row, col, "CCCC"))
//          System.out.println("pattern found at " + row + ", " + col);
//          return CompletableFuture.completedFuture(true);
//      }
//    }
    return CompletableFuture.completedFuture(false);
  }

  // This function searches in all 8-direction from point (row, col) in grid[][]
  private static boolean search2D(char[][] grid, int row, int col, String word) {
    // If first character of word doesn't match with given starting point in grid.
    if (grid[row][col] != word.charAt(0)) {
      return false;
    }

    int len = word.length();

    // Search word in all 8 directions
    // starting from (row,col)
    for (int dir = 0; dir < 8; dir++) {
      // Initialize starting point for current direction
      int k;
      int rd = row + x[dir];
      int cd = col + y[dir];

      // First character is already checked, match remaining characters
      for (k = 1; k < len; k++) {
        // If out of bound break
        if (rd >= R || rd < 0 || cd >= C || cd < 0)
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

  // Searches given word in a given
  // matrix in all 8 directions
  static void patternSearch(char[][] grid, String word) {
    // Consider every point as starting point and search given patter
    for (int row = 0; row < R; row++) {
      for (int col = 0; col < C; col++) {
        if (search2D(grid, row, col, word))
          System.out.println("pattern found at " + row +
                  ", " + col);
      }
    }
  }


  private char[][] createArray(String[] dnaList) {
    char[][] mutant;
    int sizePerColumn = dnaList[0].length();
    int sizePerRows = dnaList.length;
    mutant = new char[sizePerRows][sizePerColumn];
    for (int i = 0; i < sizePerRows; i++) {
      if (sizePerColumn != dnaList[i].length()) {
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