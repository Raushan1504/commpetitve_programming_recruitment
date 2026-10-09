# Count Subarrays With a Given Sum (Prefix Sum + HashMap)

## Problem

Given an array of `n` integers and a target sum `x`, count the number of contiguous subarrays whose elements add up to exactly `x`.

**Input**
```
n x
a1 a2 a3 ... an
```

**Output**
A single integer: the number of subarrays with sum `x`.

**Example**

```
5 7
2 4 1 2 7
```
Output: `3`

The matching subarrays are `[2, 4, 1]`, `[4, 1, 2]` and `[7]`.

---

## Key Idea

Define the **prefix sum** `P[i]` as the sum of the first `i` elements (`P[0] = 0`).

The sum of the subarray from index `j` to `i - 1` is:

```
sum(j..i-1) = P[i] - P[j]
```

We want this to equal `x`, so for each position `i` we need the number of earlier prefix sums `P[j]` such that:

```
P[j] = P[i] - x
```

So while scanning the array from left to right, we:

1. Update the running prefix sum `curr_sum`.
2. Look up how many times the value `curr_sum - target` has already appeared as a prefix sum. Each earlier occurrence is the start of one valid subarray ending at the current index, so add that count to the answer.
3. Record the current `curr_sum` in the map (increase its count by 1).

### Why `p_count.put(0L, 1L)` at the start?

This represents the empty prefix (`P[0] = 0`). Without it, subarrays that start at index 0 would never be counted. For example, if the first few elements already sum to `x`, then `curr_sum - target = 0`, and we need the map to say that `0` has appeared once.

### Why check *before* inserting?


---

## Walkthrough

For `arr = [2, 4, 1, 2, 7]`, `x = 7`. The map starts as `{0: 1}`.

| i | arr[i] | curr_sum | look up (curr_sum - 7) | found | total | map after insert |
|---|--------|----------|------------------------|-------|-------|------------------|
| 0 | 2 | 2 | -5 | 0 | 0 | {0:1, 2:1} |
| 1 | 4 | 6 | -1 | 0 | 0 | {0:1, 2:1, 6:1} |
| 2 | 1 | 7 | 0 | 1 | 1 | {0:1, 2:1, 6:1, 7:1} |
| 3 | 2 | 9 | 2 | 1 | 2 | {0:1, 2:1, 6:1, 7:1, 9:1} |
| 4 | 7 | 16 | 9 | 1 | 3 | {0:1, 2:1, 6:1, 7:1, 9:1, 16:1} |

Final answer: **3**

- At `i = 2`, prefix `0` was found, which gives `[2, 4, 1]`.
- At `i = 3`, prefix `2` was found, which gives `[4, 1, 2]`.
- At `i = 4`, prefix `9` was found, which gives `[7]`.

---

## Code Structure

- `solve(long[] arr, long target)` computes the prefix sums and uses a `HashMap<Long, Long>` that maps each prefix sum to the number of times it has appeared.
- `main` reads `n` and `x`, reads the array using `BufferedReader` and `StringTokenizer` (fast input for large `n`), calls `solve`, and prints the result.
- [The buffer reader and string token are for cses boilderplate so that the code can read the input]

`long` is used for the values, the running sum, the map counts and the answer, because sums and the number of subarrays can exceed the range of `int`.

---

## Submission Result

The solution was accepted on all test cases.


![Accepted submission](src/accepted_prefix_sum.png)

---

## Complexity

### Time: O(n)

- We make a single pass over the array.
- Each step does a constant number of HashMap operations (`getOrDefault` and `put`), which take O(1) on average.
- Total: n steps with O(1) work each, so O(n).

In the worst case, many hash collisions can slow individual operations, but Java's `HashMap` converts heavily collided buckets into balanced trees, so each operation is at most O(log n) even then.

### Space: O(n)

- The map stores one entry per **distinct** prefix sum seen so far, including the initial `0`.
- With `n` elements there are at most `n + 1` distinct prefix sums, so the map holds up to `n + 1` entries.
- This is the price we pay for not needing the positivity assumption (see below).

---

## Comparison With the Sliding Window Solution

| | Sliding Window | Prefix Sum + HashMap |
|---|---|---|
| Time | O(n) | O(n) average |
| Extra space | O(1) | O(n) |
| Works with zeros / negatives | No | **Yes** |
| Requires all elements positive | Yes | No |

The prefix sum approach does not depend on the sum increasing as the window grows, so it is correct for **any** integers, including negatives and zeros. When all elements are positive, the sliding window is lighter on memory; otherwise, use this one.
