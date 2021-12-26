from binary_tree_node import BinaryTreeNode
from test_framework import generic_test


# 09:15
# 09:22 finished writing code
# 09:23 finished debugging. all working 1st shot :)
def is_symmetric(tree: BinaryTreeNode) -> bool:
    if not tree:
        return True
    return check_symmetric(tree.left, tree.right)


def check_symmetric(left: BinaryTreeNode, right: BinaryTreeNode) -> bool:
    if not left and not right:
        return True
    elif left and right:
        # compare opposite child nodes
        return left.data == right.data and \
               check_symmetric(left.left, right.right) and \
               check_symmetric(left.right, right.left)
    else:
        # only one of the child nodes is missing
        return False


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('is_tree_symmetric.py',
                                       'is_tree_symmetric.tsv', is_symmetric))
