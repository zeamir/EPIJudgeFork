package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MinimumWeightPathInATriangle {
    @EpiTest(testDataFile = "minimum_weight_path_in_a_triangle.tsv")


    public static int minimumPathTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) {
            return 0;
        }
        int[] prevLevlMins = new int[1];
        prevLevlMins[0] = triangle.get(0).get(0);
        for (int level = 1; level < triangle.size(); level++) {
            List<Integer> items = triangle.get(level);
            int[] currLevelMins = new int[items.size()];
            for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
                // consider min from right path
                int minFromTopLeft = itemIndex > 0 ? prevLevlMins[itemIndex - 1] : Integer.MAX_VALUE;
                int minFromTopRight = itemIndex < items.size() - 1 ? prevLevlMins[itemIndex] : Integer.MAX_VALUE;
                currLevelMins[itemIndex] = items.get(itemIndex) + Math.min(minFromTopLeft, minFromTopRight);
            }
            prevLevlMins = currLevelMins;
        }

        return Arrays.stream(prevLevlMins).min().getAsInt();

    }

    public static int minimumPathTotal_recursice(List<List<Integer>> triangle) {
        // Implement this placeholder.
        if (triangle == null || triangle.isEmpty()) {
            return 0;
        }
        int[][] cache = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];
        for (int[] arr : cache) {
            Arrays.fill(arr, -1);
        }
        return minPath(0, 0, triangle, cache);
    }

    private static int minPath(int levelIndex, int itemIndex, List<List<Integer>> triangle, int[][] cache) {
        if (levelIndex >= triangle.size()) {
            return 0;
        }
        if (cache[levelIndex][itemIndex] > -1) {
            return cache[levelIndex][itemIndex];
        }
        int minLeftPath = minPath(levelIndex + 1, itemIndex, triangle, cache);
        int minRightPath = minPath(levelIndex + 1, itemIndex + 1, triangle, cache);
        int minTotal = triangle.get(levelIndex).get(itemIndex) + Math.min(minLeftPath, minRightPath);
        cache[levelIndex][itemIndex] = minTotal;
        return minTotal;
    }

    public static int minimumPathTotal_epi_solution(List<List<Integer>> triangle) {
        if (triangle.isEmpty()) {
            return 0;
        }

        // As we iterate, prevRow stores the minimum path sum to each entry in
        // triangle.get(i - 1).
        List<Integer> prevRow = new ArrayList<>(triangle.get(0));
        for (int i = 1; i < triangle.size(); ++i) {
            // Stores the minimum path sum to each entry in triangle.get(i).
            List<Integer> currRow = new ArrayList<>(triangle.get(i));
            // For the first element.
            currRow.set(0, currRow.get(0) + prevRow.get(0));
            for (int j = 1; j < currRow.size() - 1; ++j) {
                currRow.set(j, currRow.get(j) +
                        Math.min(prevRow.get(j - 1), prevRow.get(j)));
            }
            // For the last element
            currRow.set(currRow.size() - 1, currRow.get(currRow.size() - 1) +
                    prevRow.get(prevRow.size() - 1));

            prevRow = currRow;
        }
        return Collections.min(prevRow);
    }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MinimumWeightPathInATriangle.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
