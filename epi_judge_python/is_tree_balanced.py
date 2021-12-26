# also available on leetcode
# ש# #https://leetcode.com/problems/balanced-binary-tree/submissions/
from typing import NamedTuple

from binary_tree_node import BinaryTreeNode
from test_framework import generic_test


class IsBalancedRes(NamedTuple):
    is_balanced: bool
    height: int


# @dataclasses.dataclass
# class IsBalancedRes:
#     is_balanced: bool
#     height: int


def is_balanced_binary_tree(tree: BinaryTreeNode) -> bool:
    res = is_balanced(tree)
    return res.is_balanced


def is_balanced(node: BinaryTreeNode) -> IsBalancedRes:
    """
    returns Tuple of is_balanced, left height, right height
    """
    if node is None:
        return IsBalancedRes(True, -1)
    res_left = is_balanced(node.left)
    if not res_left.is_balanced:
        return res_left
    res_right = is_balanced(node.right)
    if not res_right.is_balanced:
        return res_right
    if abs(res_left.height - res_right.height) <= 1:
        return IsBalancedRes(True, 1 + max(res_left.height, res_right.height))
    else:
        return IsBalancedRes(False, 0)


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('is_tree_balanced.py',
                                       'is_tree_balanced.tsv',
                                       is_balanced_binary_tree))
