from typing import List

from test_framework import generic_test


def buy_and_sell_stock_once(prices: List[float]) -> float:
    # TODO - you fill in here.
    # 100, 130, 90, 80, 140, 40, 100
    # lowest so far
    # 100, 100, 90, 90, 90, 40,  40
    # 0   30.  0.  -10 50.  0.  60

    max_profit = 0
    lowest = prices[0]
    for val in prices:
        lowest = min(lowest, val)
        max_profit = max(max_profit, val - lowest)

    return max_profit


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('buy_and_sell_stock.py',
                                       'buy_and_sell_stock.tsv',
                                       buy_and_sell_stock_once))
