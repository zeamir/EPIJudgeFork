import functools
from typing import List

from test_framework import generic_test
from test_framework.test_utils import enable_executor_hook


# Returns the number of valid entries after deletion.
# noinspection PyPep8Naming
def delete_duplicates(A: List[int]) -> int:
    #
    # 0 1 2 3 4 5 6 7 8 9
    # 1 2 2 3 3 3 3 3 6 6
    # 1 2 3 6 3 3 3 3 6 6
    #                 ^
    #
    # i:      : 8
    # next_pos: 4
    # curr_num: 6

    if not A:
        return 0

    next_pos = 1
    curr_num = A[0]
    for i in range(1, len(A)):
        if A[i] != curr_num:
            curr_num = A[i]
            A[next_pos] = curr_num
            next_pos += 1

    return next_pos


@enable_executor_hook
def delete_duplicates_wrapper(executor, A):
    idx = executor.run(functools.partial(delete_duplicates, A))
    return A[:idx]


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('sorted_array_remove_dups.py',
                                       'sorted_array_remove_dups.tsv',
                                       delete_duplicates_wrapper))
