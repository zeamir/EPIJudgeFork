from typing import List

from test_framework import generic_test


# writing time: 5 minutes:
# debug time: 2 min
def search_first_of_k(A: List[int], k: int) -> int:
    s = 0
    e = len(A) - 1
    # find first index
    first_index = -1
    while s <= e:
        m = int((s + e) / 2)
        if A[m] == k:
            first_index = m
            # first index may still be in left part
            e = m - 1
        elif A[m] < k:
            # need to keep looking to the right
            s = m + 1
        else:  # A[m] > k. need to keep looking to the left
            e = m - 1

    return first_index


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('search_first_key.py',
                                       'search_first_key.tsv',
                                       search_first_of_k))
