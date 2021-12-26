import functools
from typing import List, Set

from test_framework import generic_test
from test_framework.test_utils import enable_executor_hook

WHITE, GREY, BLACK = range(3)


class GraphVertex:
    def __init__(self) -> None:
        self.color = WHITE
        self.edges: List['GraphVertex'] = []


# the book's solution approach using graph colors
def is_deadlocked(graph: List[GraphVertex]) -> bool:
    # algorithm:
    # for each vertex in the graph, run dfs.
    # whenever "entering" a vertex, color it grey  it is searching and when exising a vertex (when dfs is over)
    # flag it as visited.
    # if during a dfs from a vertex is it attempted to be entered again, then there is a cycle which means a deadlock

    # how to flag a vertex as "reaching" or visited
    # without modifying the Vertex class, we can have a set of ActiveSearch and Visited
    # set of nodes for with deadlock search is active and it has not yet been determined this node
    #   is not part of a deadlock
    # deadlock_be ing_checked = set()
    # this list contains nodes that are not part of a deadlock cycle

    # 22 min to write:
    # deadlock_free = set()
    for v in graph:
        if v.color == BLACK:
            # node already determined as not part of deadlock
            continue

        deadlock = contains_deadlock_dfs(v)
        if deadlock:
            return True

    return False


# similar to book approach, using colors
def contains_deadlock_dfs(v: GraphVertex) -> bool:
    if v.color == BLACK:
        return False
    if v.color == GREY:
        return True

    v.color = GREY
    # go over all edges
    deadlock_found = any(contains_deadlock_dfs(next_v) for next_v in v.edges)
    if deadlock_found:
        return True

    # flag this vertex that it is not part of a deadlock
    v.color = BLACK
    return False

def is_deadlocked_amirz(graph: List[GraphVertex]) -> bool:
    # algorithm:
    # for each vertex in the graph, run dfs.
    # whenever "entering" a vertex, flag it is searching and when exising a vertex (when dfs is over)
    # flag it as visited.
    # if during a dfs from a vertex is it attempted to be entered again, then there is a cycle which means a deadlock

    # how to flag a vertex as "reaching" or visited
    # without modifying the Vertex class, we can have a set of ActiveSearch and Visited
    # set of nodes for with deadlock search is active and it has not yet been determined this node
    #   is not part of a deadlock
    # deadlock_be ing_checked = set()
    # this list contains nodes that are not part of a deadlock cycle

    # 22 min to write:
    deadlock_free = set()
    for v in graph:
        if v in deadlock_free:
            continue

        deadlock_being_checked = set()
        deadlock = contains_deadlock_dfs_amirz(v, deadlock_free, deadlock_being_checked)
        if deadlock:
            return True

    return False



def contains_deadlock_dfs_amirz(v: GraphVertex,
                                deadlock_free: Set[GraphVertex],
                                deadlock_being_checked: Set[GraphVertex]) -> bool:
    deadlock_being_checked.add(v)
    # go over all edges
    for v2 in v.edges:
        if v2 in deadlock_being_checked:
            # found deadlock. no point to continue
            return True
        if v2 in deadlock_free:
            continue
        # flag node as being checked:
        if contains_deadlock_dfs_amirz(v2, deadlock_free, deadlock_being_checked):
            # found deadlock. no point to continue
            return True

    deadlock_being_checked.remove(v)
    deadlock_free.add(v)
    return False


@enable_executor_hook
def is_deadlocked_wrapper(executor, num_nodes, edges):
    if num_nodes <= 0:
        raise RuntimeError('Invalid num_nodes value')
    graph = [GraphVertex() for _ in range(num_nodes)]

    for (fr, to) in edges:
        if fr < 0 or fr >= num_nodes or to < 0 or to >= num_nodes:
            raise RuntimeError('Invalid vertex index')
        graph[fr].edges.append(graph[to])

    return executor.run(functools.partial(is_deadlocked, graph))


if __name__ == '__main__':
    exit(
        generic_test.generic_test_main('deadlock_detection.py',
                                       'deadlock_detection.tsv',
                                       is_deadlocked_wrapper))
