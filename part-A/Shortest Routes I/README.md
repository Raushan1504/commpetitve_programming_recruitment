# Shortest Routes From City 1 (Dijkstra's Algorithm)

## Problem

There are `n` cities connected by `m` **one-way** flights. Each flight goes from city `u` to city `v` and has a length (weight) `w`. Find the shortest distance from city `1` to every city.

**Input**
```
n m
u1 v1 w1
u2 v2 w2
...
um vm wm
```

**Output**
`n` integers: the shortest distance from city 1 to cities `1, 2, ..., n`.

**Example**

```
3 4
1 2 6
1 3 2
3 2 3
1 3 4
```
Output: `0 5 2`

- City 1 is the start, so its distance is `0`.
- City 3 is best reached directly with the flight of length `2` (the other `1 -> 3` flight has length `4`).
- City 2 is best reached through city 3: `1 -> 3 -> 2` costs `2 + 3 = 5`, which beats the direct flight of length `6`.

---

## Key Idea

This is a **single-source shortest path** problem on a weighted graph with non-negative weights, which is exactly what **Dijkstra's algorithm** solves.

The idea is greedy: always process the unvisited city that is currently closest to the source. Once we pick that city, its distance is final, because every other route to it would have to pass through a city that is at least as far away (and weights are non-negative, so extra edges can only add distance).

For each city we pop, we try to **relax** its outgoing edges: if going through the current city gives a shorter path to a neighbor, we update the neighbor's distance and push it into the queue.

---

## How the Code Works

### Data structures

- `graph`: an **adjacency list**. `graph.get(u)` holds all `Edge` objects leaving city `u`. Cities are converted to 0-indexed (`u--`, `v--`).
- `Edge(city, distance)`: a flight to `city` with length `distance`.
- `dist[]`: `dist[i]` is the best known distance from city 1 to city `i`. It starts at `Long.MAX_VALUE` (infinity), except `dist[0] = 0`.
- `PriorityQueue<Node>`: a **min-heap** that always gives us the node with the smallest distance first.
- `Node(city, distance)`: an entry in the queue. It implements `Comparable<Node>`, and `compareTo` uses `Long.compare(this.distance, other.distance)`. This returns a negative number, zero or a positive number depending on whether this node's distance is smaller than, equal to or larger than the other's. That is what makes the priority queue order nodes by smallest distance first.

### Steps

1. Build the adjacency list from the `m` edges.
2. Set `dist[0] = 0` and push `(city 0, distance 0)` into the priority queue.
3. Repeat until the queue is empty:
    1. Pop the node with the smallest distance.
    2. **Skip it if it is stale:** if `currDist > dist[currNode]`, a shorter path to this city was already found after this entry was added, so we ignore it.
    3. For each outgoing edge, compute `newDist = currDist + edgeWeight`. If `newDist < dist[neighbor]`, update `dist[neighbor]` and push the neighbor with its new distance.
4. Print `dist[]`.

### Why the "stale entry" check?

Java's `PriorityQueue` has no efficient "decrease key" operation. Instead, whenever we find a shorter path, we simply **add a new entry** and leave the old, longer one in the queue. When the old entry is eventually popped, `currDist > dist[currNode]` is true and we skip it. This technique is called *lazy deletion*.

---

## Walkthrough

For the example above (0-indexed: city 1 becomes node 0, and so on):

Edges: `0 -> 1 (6)`, `0 -> 2 (2)`, `2 -> 1 (3)`, `0 -> 2 (4)`

Start: `dist = [0, INF, INF]`, queue = `[(0, 0)]`

| Step | Pop | Action | dist | Queue after |
|------|-----|--------|------|-------------|
| 1 | (0, 0) | Edge to 1: 0+6=6 < INF, update. Edge to 2: 0+2=2 < INF, update. Edge to 2: 0+4=4, not < 2, ignore. | [0, 6, 2] | (2,2), (1,6) |
| 2 | (2, 2) | Edge to 1: 2+3=5 < 6, update. | [0, 5, 2] | (1,5), (1,6) |
| 3 | (1, 5) | City 1 has no outgoing edges. | [0, 5, 2] | (1,6) |
| 4 | (1, 6) | Stale: 6 > dist[1] = 5, skip. | [0, 5, 2] | empty |

Final output: **0 5 2**

Step 4 shows the stale-entry check in action.

---

## Submission Result

The solution was accepted on all test cases.

![Accepted submission](accepted_dijkstra.png)

---

## Complexity

Let `n` be the number of cities and `m` the number of flights.

### Time: O((n + m) log n), usually written O(m log n)

- Building the graph takes O(n + m).
- Each edge can cause at most **one** successful relaxation, so at most `m` entries are ever pushed into the priority queue (plus the initial one).
- Every push and every poll on the priority queue costs O(log m). Since `m <= n²`, we have `log m <= 2 log n`, so this is O(log n).
- Each of the up to `m + 1` entries is pushed once and polled once, giving O(m log n) in total.
- Edge scanning happens only for non-stale pops, so across the whole run each edge is examined once (O(m)).

### Space: O(n + m)

- The adjacency list stores `n` lists and `m` edges: O(n + m).
- `dist[]` takes O(n).
- The priority queue can hold up to O(m) entries at once because of lazy deletion (duplicate entries for the same city).

---

## Assumptions and Limitations

- **All edge weights must be non-negative.** Dijkstra's correctness depends on a finalized distance never being improved later, which fails with negative edges. For negative weights, use **Bellman-Ford** (O(n·m)) instead.
- The graph is **directed**: each input line adds only the edge `u -> v`. For an undirected graph, you would also add `v -> u`.
- If a city is **unreachable** from city 1, its distance stays `Long.MAX_VALUE` and that value is printed. This problem guarantees every city is reachable, so it does not occur here.
- `long` is used for distances because path lengths can be as large as about `10^9 * 10^5`, which overflows `int`.