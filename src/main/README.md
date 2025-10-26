# Minimum Spanning Tree Analysis: Kruskal vs Prim

**Student:** Nazerke Abzhamal  
**Group:** SE-2422

This report presents a comprehensive theoretical and practical analysis of **Kruskal’s** and **Prim’s** algorithms for computing the **Minimum Spanning Tree (MST)**.  
The project is implemented in Java and includes benchmarking across multiple graph datasets of varying sizes.

---

## 1. Theoretical Overview

### 1.1. Kruskal’s Algorithm

Kruskal’s algorithm constructs the MST by continuously adding the smallest edge that doesn’t create a cycle, using the **Union-Find** data structure for efficiency.

T(Kruskal) (V, E) = O(E log E)

Where:
- \( V \) — number of vertices
- \( E \) — number of edges

---

### 1.2. Prim’s Algorithm

Prim’s algorithm starts from any vertex and expands the MST by repeatedly choosing the smallest edge connecting the current tree to a new vertex.  
It is particularly efficient when implemented with a **min-priority queue** and adjacency list.

T(Prim) (V,E)=O(E log V)

---

## 2. Time and Space Complexity

| Algorithm | Time Complexity | Space Complexity | Best Use Case |
|:-----------|:----------------|:-----------------|:---------------|
| **Prim’s** | \( O(E \log V) \) | \( O(V + E) \) | Dense graphs |
| **Kruskal’s** | \( O(E \log E) \) | \( O(V + E) \) | Sparse graphs |

**Summary:**
- Kruskal performs better on sparse graphs since it only processes sorted edges.
- Prim is more efficient for dense graphs, leveraging adjacency structures.

---

## 3. Project Structure

KruskalAndPrim/

├── results/

│ └── benchmark_results.csv

├── src/

│ ├── main/java/org/example/

│ │ ├── algorithms/ # Kruskal.java, Prim.java

│ │ ├── graph/ # Graph.java, Edge.java, DisjointSet.java

│ │ ├── io/ # GraphLoader.java, ResultsWriter.java

│ │ └── model/ # MSTResult.java, MSTOutput.java

│ └── resources/json/ # graphs_small.json ... graphs_extra_large.json

└── tests/

├── PerformanceTest.java

├── CorrectnessTest.java

└── GraphLoaderTest.java

---

## 4. Example Graph Visualization

Below is an example of a small input graph used for MST computation:

A --(4)-- B

A --(3)-- C

B --(1)-- C

B --(2)-- D

C --(4)-- D

D --(2)-- E

E --(6)-- F

This representation illustrates how weighted edges connect the vertices, forming the basis for MST computation in both algorithms.

---

## 5. Practical Benchmark Results

Performance tests were executed on multiple datasets.  
The following table highlights selected benchmark data:

| GraphID | Algorithm | Vertices | Edges | ExecutionTimeMs | OperationsCount | TotalCost |
|:--------:|:-----------:|:----------:|:------:|:----------------:|:----------------:|:------------:|
| 1 | Kruskal | 15 | 30 | 0.039 | 61 | 59 |
| 1 | Prim | 15 | 30 | 0.118 | 60 | 59 |
| 2 | Kruskal | 8 | 16 | 0.014 | 33 | 35 |
| 2 | Prim | 8 | 16 | 0.027 | 32 | 35 |
| 3 | Kruskal | 20 | 40 | 0.017 | 84 | 114 |
| 3 | Prim | 20 | 40 | 0.024 | 83 | 114 |
| 6 | Kruskal | 284 | 852 | 0.299 | 1939 | 1235 |
| 6 | Prim | 284 | 852 | 0.378 | 1922 | 1235 |
| 16 | Kruskal | 672 | 2016 | 0.751 | 4612 | 3173 |
| 16 | Prim | 672 | 2016 | 1.026 | 4616 | 3173 |
| 23 | Kruskal | 709 | 2127 | 0.447 | 4918 | 3214 |
| 23 | Prim | 709 | 2127 | 0.471 | 4879 | 3214 |

 
*Full dataset is available in `results/benchmark_results.csv`.*

---

## 6. Comparative Analysis

| Graph Type | Best Algorithm | Reason |
|:-------------|:----------------|:--------|
| Sparse Graphs | **Kruskal** | Faster due to simpler edge sorting and fewer priority queue operations |
| Dense Graphs | **Prim** | Better performance using adjacency lists and heaps |
| Large Graphs | **Kruskal** | More predictable runtime and efficient sorting |
| Fully Connected Graphs | **Prim** | Exploits adjacency efficiently |

**Observations:**
- Kruskal’s algorithm achieved lower execution times in most cases.
- Both algorithms produced identical MST costs, validating correctness.
- For larger graphs, Kruskal showed more stable scalability.

---

## 7. Conclusions

Based on both theoretical and empirical findings:

- **Kruskal’s Algorithm** is recommended for **sparse** or **edge-heavy** graphs.
- **Prim’s Algorithm** is preferable for **dense** graphs with many connections.
- Both algorithms consistently deliver the same MST cost, ensuring correctness.

---

## 8. Recommendations

Use **Prim’s Algorithm** for dense networks and adjacency matrix representations.  
Use **Kruskal’s Algorithm** for sparse or large-scale graphs.  
For graph libraries or dynamic networks, a hybrid approach can be adopted based on edge density.

---