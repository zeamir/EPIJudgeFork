from typing import List

from test_framework import generic_test, test_utils


def generate_balanced_parentheses(num_pairs: int) -> List[str]:
    # algo, recursively add parens. after adding each perens a recursive
    # call is made.
    matched_parens = [' ' for _ in range(2 * num_pairs)]
    res = []
    construct_matched_parens(matched_parens, 0, 0, 0, res)
    # print(f'{num_pairs}: {len(res)}')
    return res


def construct_matched_parens(matched_parens: List[str], idx: int, left: int, right: int, res: List[str]) -> None:
    max_len = len(matched_parens)
    if idx == max_len:
        res.append("".join(matched_parens))

    # check if we can add left paren:
    if left < max_len / 2:
        matched_parens[idx] = '('
        construct_matched_parens(matched_parens, idx + 1, left + 1, right, res)

    # check if we can add right paren. right parens can be added if their number is less than left parnts
    if right < left:
        matched_parens[idx] = ')'
        construct_matched_parens(matched_parens, idx + 1, left, right + 1, res)

    # _ _ _ _ _ _ (6)
    # ( ) )


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('enumerate_balanced_parentheses.py',
                                       'enumerate_balanced_parentheses.tsv',
                                       generate_balanced_parentheses,
                                       test_utils.unordered_compare))
