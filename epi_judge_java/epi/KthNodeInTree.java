package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
public class KthNodeInTree {
  public static class BinaryTreeNode<T> extends TreeLike<T, BinaryTreeNode<T>> {
    public T data;
    public BinaryTreeNode<T> left, right;
    public int size;

    public BinaryTreeNode(T data, BinaryTreeNode<T> left,
                          BinaryTreeNode<T> right, int size) {
      this.data = data;
      this.left = left;
      this.right = right;
      this.size = size;
    }

    @Override
    public T getData() {
      return data;
    }

    @Override
    public BinaryTreeNode<T> getLeft() {
      return left;
    }

    @Override
    public BinaryTreeNode<T> getRight() {
      return right;
    }
  }

	public static BinaryTreeNode<Integer>
	findKthNodeBinaryTree(BinaryTreeNode<Integer> tree, int k) {

		// Implement this placeholder.
		return find(tree, k);
	}

	public static BinaryTreeNode<Integer> find(BinaryTreeNode<Integer> root, int k) {
		if (root == null || k > root.size) {
			return null;
		}
		int leftSize = root.left == null ? 0 : root.left.size;
		if (leftSize > 0 && k <= leftSize) {
			return find(root.left, k);
		}
		if (k == leftSize + 1) {
			return root;
		}
		// if we got here it meanst result must be in rigth subtree
		return find(root.right, k - 1 - leftSize);
	}
  public static BinaryTreeNode<Integer>
  convertToTreeWithSize(BinaryTree<Integer> original) {
    if (original == null)
      return null;
    BinaryTreeNode<Integer> left = convertToTreeWithSize(original.left);
    BinaryTreeNode<Integer> right = convertToTreeWithSize(original.right);
    int lSize = left == null ? 0 : left.size;
    int rSize = right == null ? 0 : right.size;
    return new BinaryTreeNode<>(original.data, left, right, 1 + lSize + rSize);
  }

  @EpiTest(testDataFile = "kth_node_in_tree.tsv")
  public static int findKthNodeBinaryTreeWrapper(TimedExecutor executor,
                                                 BinaryTree<Integer> tree,
                                                 int k) throws Exception {
    BinaryTreeNode<Integer> converted = convertToTreeWithSize(tree);

    BinaryTreeNode<Integer> result =
        executor.run(() -> findKthNodeBinaryTree(converted, k));

    if (result == null) {
      throw new TestFailure("Result can't be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KthNodeInTree.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
