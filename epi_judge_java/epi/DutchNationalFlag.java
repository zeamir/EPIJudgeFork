package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class DutchNationalFlag {
  public enum Color { RED, WHITE, BLUE }

	public static void dutchFlagPartition(int pivotIndex, List<Color> arrayList) {
		//System.out.println("******************");
		//System.out.println("before: " + arrayList + "; pivot index = " + pivotIndex + "; pivot value: " + arrayList.get(pivotIndex).ordinal());
		if (pivotIndex > arrayList.size() - 1) {
			return;
		}
		//printArray(arrayList);
		int nextLarger = arrayList.size() - 1;
		int pivot = arrayList.get(pivotIndex).ordinal();
		int nextSmaller = 0;
		while (nextSmaller <= nextLarger) {
			if (arrayList.get(nextSmaller).ordinal() > pivot) {
				Collections.swap(arrayList, nextSmaller, nextLarger--);
				//printArray(arrayList);
			} else {
				nextSmaller++;
			}
		}

		//System.out.println("intermediate array: " + arrayList + "; nextSmaller = " + nextSmaller + "; nextLarger = " + nextLarger);

		// now fill the pivot
		int nextPivot = nextSmaller - 1;
		int pivotCandidate = 0;
		printArray(arrayList);
		while (pivotCandidate <= nextPivot) {
			if (arrayList.get(pivotCandidate).ordinal() == pivot) {
				Collections.swap(arrayList, pivotCandidate, nextPivot--);
				//printArray(arrayList);
			} else {
				pivotCandidate++;
			}
		}
		//printArray(arrayList);
	}

	private static void printArray(List<Color> arrayList) {
		System.out.println("-->" + arrayList);
	}
  @EpiTest(testDataFile = "dutch_national_flag.tsv")
  public static void dutchFlagPartitionWrapper(TimedExecutor executor,
                                               List<Integer> A, int pivotIdx)
      throws Exception {
    List<Color> colors = new ArrayList<>();
    int[] count = new int[3];

    Color[] C = Color.values();
    for (int i = 0; i < A.size(); i++) {
      count[A.get(i)]++;
      colors.add(C[A.get(i)]);
    }

    Color pivot = colors.get(pivotIdx);
    executor.run(() -> dutchFlagPartition(pivotIdx, colors));

    int i = 0;
    while (i < colors.size() && colors.get(i).ordinal() < pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() == pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() > pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    if (i != colors.size()) {
      throw new TestFailure("Not partitioned after " + Integer.toString(i) +
                            "th element");
    } else if (count[0] != 0 || count[1] != 0 || count[2] != 0) {
      throw new TestFailure("Some elements are missing from original array");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DutchNationalFlag.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
