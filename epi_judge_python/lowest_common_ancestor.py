# Also available on leetcode
# https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

import functools
from typing import Optional, NamedTuple

from binary_tree_node import BinaryTreeNode
from test_framework import generic_test
from test_framework.binary_tree_utils import must_find_node, strip_parent_link
from test_framework.test_failure import TestFailure
from test_framework.test_utils import enable_executor_hook


# 09:44 - start writing after reading the general idea of the solution
# if the LCA is in the current path of the parent (meaning not in both left and right subtrees)
#   then the LCA will be set

class LcaRes(NamedTuple):
    found_so_far: int  # number of nodes in the current path
    lca: Optional[BinaryTreeNode]  # if lca not found yet then None

# this is a more complex solution that will terminate as soon we found the 2 children
def lca(tree: BinaryTreeNode, node0: BinaryTreeNode,
        node1: BinaryTreeNode) -> Optional[BinaryTreeNode]:
    def calc_lca(curr: BinaryTreeNode,
                 found_so_far: int  # number of LCAs found so far
                 ) -> LcaRes:
        if not curr:
            return LcaRes(found_so_far, None)
        found_in_curr = [node0, node1].count(curr)
        if found_in_curr == 2:
            if found_in_curr == 2:
                return LcaRes(2, curr)

        found_so_far += found_in_curr
        if found_so_far == 2:
            return LcaRes(2, None)

        left_res = calc_lca(curr.left, found_so_far)
        if left_res.lca:
            return left_res
        if (left_res.found_so_far > found_so_far):
            found_in_curr += 1
        if found_in_curr == 2:
            return LcaRes(2, curr)

        found_so_far = left_res.found_so_far

        right_res = calc_lca(curr.right, found_so_far)
        if right_res.lca:
            return right_res

        if right_res.found_so_far > found_so_far:
            found_in_curr += 1

        found_so_far = right_res.found_so_far
        if found_in_curr == 2:
            return LcaRes(2, curr)
        else:
            return LcaRes(found_so_far, None)

    # todo handle edge cases that the roor is the LCA
    if not tree:
        return None

    return calc_lca(tree, 0).lca


@enable_executor_hook
def lca_wrapper(executor, tree, key1, key2):
    strip_parent_link(tree)
    result = executor.run(
        functools.partial(lca, tree, must_find_node(tree, key1),
                          must_find_node(tree, key2)))

    if result is None:
        raise TestFailure('Result can\'t be None')
    return result.data


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('lowest_common_ancestor.py',
                                       'lowest_common_ancestor.tsv',
                                       lca_wrapper))
