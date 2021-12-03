package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeSymmetric {
  @EpiTest(testDataFile = "is_tree_symmetric.tsv")

  public static boolean isSymmetric(BinaryTreeNode<Integer> tree) {
	  // Implement this placeholder.
	  // algo: for each of the subtrees (subtree1, subtree2) check if subtree2.left = subtree2.right
	  if (tree == null) {
		  return true;
	  }
	  return isSymmetric(tree.left, tree.right);
  }

	static boolean isSymmetric(BinaryTreeNode<Integer> leftTree, BinaryTreeNode<Integer> rightTree) {
		// if one is null the other is not return false;
		if (leftTree == null && rightTree == null) {
			return true;
		}
		if (leftTree != null && rightTree != null) {
			if (!leftTree.data.equals(rightTree.data)) {
				return false;
			}
			return 	isSymmetric(leftTree.right, rightTree.left) &&
					isSymmetric(leftTree.left, rightTree.right);
		}
		return false;
	}

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeSymmetric.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
