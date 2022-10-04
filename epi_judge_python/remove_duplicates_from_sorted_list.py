from typing import Optional

from list_node import ListNode
from test_framework import generic_test


def remove_duplicates(L: ListNode) -> Optional[ListNode]:
    curr = L
    while curr:
        next_distinct: ListNode = curr.next
        while next_distinct and next_distinct.data == curr.data:
            next_distinct = next_distinct.next
        curr.next = next_distinct
        curr = next_distinct
    return L


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main(
            'remove_duplicates_from_sorted_list.py',
            'remove_duplicates_from_sorted_list.tsv', remove_duplicates))
