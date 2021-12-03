package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
public class LowestCommonAncestorWithParent {

  public static BinaryTree<Integer> lca(BinaryTree<Integer> node0,
                                        BinaryTree<Integer> node1) {
	  // Implement this placeholder.
	  // alog:
	  // 1. calc the height of node0 and node1.
	  // 2. move from the node with the greater height up till it reaches the level of the other node.
	  // 3. for simultaneously in the two nodes up, until the parent of the two nodes are equal. this is the LCA
	  // edge cases
	  // if node0 == node1 -> the LCA is one of them.

	  if (node0 == null || node1 == null) {
	  	return null;
	  }
	  int h0 = calcHeight(node0);
	  int h1 = calcHeight(node1);
	  int heightDiff = Math.abs(h1 - h0);
	  // make sure h1 >= h0 and node1 to have h1 and node 0 to have h0
	  if (h1 < h0) {
		  BinaryTree<Integer> tempNode = node1;
		  node1 = node0;
		  node0 = tempNode;
	  }

	  // go up for nodeA till it has the same height as NodeB;
	  while (heightDiff-- > 0) { // todo: can we use h1-- ?
		  node1 = node1.parent;
	  }

	  // here node0 and node1 have the same height;
	  while (node0 != node1) {
		  node0 = node0.parent;
		  node1 = node1.parent;
	  }
	  return node0;
  }

	private static int calcHeight(BinaryTree<Integer> node) {
		// use part to go up the tree till the parent is null;
		if (node == null) {
			return 0;
		}
		int ret = 0;
		while (node != null) {
			node = node.parent;
			ret++;
		}
		return ret;
	}
  @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TimedExecutor executor, BinaryTree<Integer> tree,
                               Integer key0, Integer key1) throws Exception {
    BinaryTree<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BinaryTree<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BinaryTree<Integer> result = executor.run(() -> lca(node0, node1));

    if (result == null) {
      throw new TestFailure("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LowestCommonAncestorWithParent.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
