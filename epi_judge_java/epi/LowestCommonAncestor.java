package epi;

import epi.test_framework.*;

public class LowestCommonAncestor {
  public static BinaryTreeNode<Integer> lca(BinaryTreeNode<Integer> tree,
                                              BinaryTreeNode<Integer> node0,
                                              BinaryTreeNode<Integer> node1) {
        // Implement this placeholder.
        // algo: do pre-order (left -> right -> root)
        // gather info about right and left children
        // need to return the first node in which has 1 node in it's left subtree and one node in it's right subtree.
        // if 1 node is not found in each subtree,
        // return left subtree count + right subtree count + 1 (if the root itself contains the data).

        // botton line after testing
        // all good except I ignored a case where the the same node0 and node1 are the same node.
        // to me 18 minutes to write
        LCAResult lcaRes = calcLCA(tree, node0, node1);
        return lcaRes.lca;
    }

    static LCAResult calcLCA(BinaryTreeNode<Integer> tree,
                             BinaryTreeNode<Integer> node0,
                             BinaryTreeNode<Integer> node1) {
        if (tree == null) {
            return new LCAResult(null, 0);
        }
        LCAResult leftRes = calcLCA(tree.left, node0, node1);
        if (leftRes.lca != null) {
            return leftRes;
        }
        LCAResult rightRes = calcLCA(tree.right, node0, node1);
        if (rightRes.lca != null) {
            return rightRes;
        }
        int foundInRoot = 0;
        if (tree == node0) {
            foundInRoot++;
        }
        if (tree == node1) {
            foundInRoot++;
        }
        if ((leftRes.nodesFound + rightRes.nodesFound + foundInRoot) == 2) {
            return new LCAResult(tree, 2);
        } else {
            return new LCAResult(null, leftRes.nodesFound + rightRes.nodesFound + foundInRoot);
        }
    }

    static class LCAResult {
        BinaryTreeNode lca;
        int nodesFound;

        LCAResult(BinaryTreeNode lca, int nodesFound) {
            this.lca = lca;
            this.nodesFound = nodesFound;
        }
    }


    @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
    public static int lcaWrapper(TimedExecutor executor,
                                 BinaryTreeNode<Integer> tree, Integer key0,
                                 Integer key1) throws Exception {
        BinaryTreeNode<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
        BinaryTreeNode<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

        BinaryTreeNode<Integer> result =
        executor.run(() -> lca(tree, node0, node1));

        if (result == null) {
            throw new TestFailure("Result can not be null");
        }
        return result.data;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "LowestCommonAncestor.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
