package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreePreorder {
  @EpiTest(testDataFile = "tree_preorder.tsv")

  // solved it a perfect solution without any mistake :-) in about 15 minutes.
  public static List<Integer> preorderTraversal(BinaryTreeNode<Integer> tree) {
	  // Implement this placeholder.
	  // algo, set tree to curr.
	  // if curr is not null
	  // 		1. push curr to stack
	  // 		2. add curr to results
	  // 3. else - curr is not null
	  //		4. we reached a dead end, need to pop from stack (it if is not empty) and continue to the right branch
	  //		5. curr = curr.right
	  Stack<BinaryTreeNode<Integer>> s = new Stack<>();
	  List<Integer> ret = new ArrayList<>();
	  BinaryTreeNode<Integer> curr = tree;
	  while (true) {
		  if (curr != null) {
			  ret.add(curr.data);
			  s.push(curr);
			  curr = curr.left;
		  } else {
			  // we reached a dead end, need to pop from stack (it if is not empty) and continue to the right branch
			  if (s.isEmpty()) {
				  return ret;
			  }
			  curr = s.pop().right;
		  }
	  }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreePreorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
