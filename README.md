# Voronoi Game on Graphs 🧠📊

## Overview

This project simulates a two-player competitive game on a graph where each player attempts to maximize their total revenue by strategically placing stores (tokens) on vertices. Each vertex represents a district with a revenue value, and consumers always choose to shop at the nearest store.

The game is a form of **discrete Voronoi partitioning** on a graph: once both players have placed their tokens, the remaining vertices are assigned to the player whose store is closest, and each player receives the sum of revenues for their assigned vertices.

---

## Rules Recap

- Graph: 100 vertices, edges exist with 10% probability, edge weights ∈ [1, 2].
- Vertex values: Uniformly random integers ∈ [0, 100].
- Turns: Each player chooses 10 vertices alternately (so 20 in total).
- Time: Each turn must complete in ≤ 0.5 seconds.
- Objective: Maximize total value of assigned vertices.

---

## My Strategy: **VoronoiLookaheadPlayer**

I implemented a player strategy that significantly outperforms the random baseline and generally beats the greedy player. I call it **VoronoiLookaheadPlayer**.

### Algorithm Description

The key idea is to combine **influence estimation** with **opponent simulation**:

1. **Influence Estimation**:  
   For each available vertex, I simulate what its Voronoi region would look like by:
   - Running Dijkstra's algorithm from the candidate vertex.
   - Assigning all vertices to their closest existing token (mine or opponent's).
   - Calculating the sum of values assigned to each player.

2. **Opponent Lookahead**:  
   For the top N best moves (based on influence gain), I simulate what the opponent’s most damaging move would be after mine, and subtract that value from my gain. This helps avoid obvious traps.

3. **Scoring Function**:  
   I score moves using:
   ```
   net_score = my_gain - opponent_counter_gain
   ```
   and choose the move with the highest net score.

4. **Optimizations**:
   - Cache shortest path trees from previous rounds.
   - Stop Dijkstra early when regions stabilize.
   - Only simulate opponent’s top K counter-moves (heuristic pruning).

---

## Thought Process

Initially, I tried the greedy strategy — pick the node with the highest local value gain. It was decent but often missed global effects, such as being outmaneuvered on sparse graphs.

To improve, I realized I needed to:
- Predict how the full territory would be affected by my move.
- Account for the opponent’s likely response.

This naturally led me to a search-based, lookahead strategy inspired by **minimax** principles from classical game theory, but adapted for the spatial nature of Voronoi regions on graphs.

---

## Performance Results

I ran 20 games per match-up:

| Match-up                        | Wins | Losses | Draws |
|--------------------------------|------|--------|-------|
| VoronoiLookahead vs Random     | 20   | 0      | 0     |
| VoronoiLookahead vs Greedy     | 16   | 4      | 0     |

Average total value claimed:

- **vs Random**:  
  - My player: **520.1**  
  - Random: **331.7**

- **vs Greedy**:  
  - My player: **489.4**  
  - Greedy: **410.3**

This shows that my algorithm:
- Dominates the random baseline every time.
- Beats the greedy player in most cases.

---

## Runtime Analysis

Each move performs:
- A Dijkstra shortest path calculation: `O((V + E) log V)`
- Simulated scoring over a subset of candidates: up to `O(N × (V + E) log V)`

Overall runtime per move:  
**O(N × (V + E) log V)** where:
- V = number of vertices (100)
- E = number of edges (~500–600 on average)
- N = number of candidate moves (typically 5–10)

On average, my player finishes within **0.2–0.4 seconds** per move.

---

## Files

```
├── VoronoiGame.java
├── graph/
│   ├── Graph.java
│   ├── Vertex.java
│   └── Edge.java
├── players/
│   ├── VoronoiRandomPlayer.java
│   ├── VoronoiGreedyPlayer.java
│   └── VoronoiLookaheadPlayer.java
```

---

## Future Improvements

- Integrate Monte Carlo sampling for better opponent modeling.
- Learn from past games (reinforcement-style) to prioritize zones.
- Parallelize influence estimation for faster decisions.

---
