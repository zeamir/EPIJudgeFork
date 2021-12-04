import collections
from typing import Deque

from test_framework import generic_test


def is_well_formed(s: str) -> bool:
    mappings = {
        "}": "{",
        "]": "[",
        ")": "("
    }
    stack: Deque[str] = collections.deque()
    for c in s:
        # if we get an opener, put it to stack
        if c in "{[(":
            stack.append(c)
        # make sure the stack contains its matching opener
        elif not stack or stack.pop() != mappings[c]:
            return False
    return len(stack) == 0


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('is_valid_parenthesization.py',
                                       'is_valid_parenthesization.tsv',
                                       is_well_formed))
