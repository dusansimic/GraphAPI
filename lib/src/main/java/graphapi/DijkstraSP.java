package graphapi;

import java.util.ArrayList;
import java.util.List;

public class DijkstraSP {
  private EdgeWeightedGraph g;
  private int start;
  private Double[] distTo;
  private WeightedEdge[] edgeTo;
  private Condition condition;

  public DijkstraSP(EdgeWeightedGraph g, int u) {
    this.g = g;
    validateVertex(u);
    start = u;
    distTo = new Double[g.V()];
    edgeTo = new WeightedEdge[g.V()];
    for (int v : g.vertices()) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }
    find(u);
  }

  public DijkstraSP(EdgeWeightedGraph g, int u, Condition condition) {
    this.g = g;
    validateVertex(u);
    start = u;
    distTo = new Double[g.V()];
    edgeTo = new WeightedEdge[g.V()];
    for (int v : g.vertices()) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }
    this.condition = condition;
    find(u);
  }

  private void find(int u) {
    distTo[u] = 0.0;

    IndexMinPQ<Double> pq = new IndexMinPQ<>();
    pq.enqueue(u, distTo[u]);

    while (!pq.isEmpty()) {
      int v = pq.dequeue();
      for (WeightedEdge e : g.adj(v)) {
        if (relax(e, v)) {
          int w = e.other(v);
          if (pq.contains(w)) {
            pq.changeKey(w, e.weight());
          } else {
            pq.enqueue(w, e.weight());
          }
        }
      }
    }
  }

  private boolean relax(WeightedEdge e, int v) {
    int u = e.other(v);
    double w = e.weight();
    if (distTo[u] > distTo[v] + w && (condition != null ? condition.condition(e, v) : true)) {
      distTo[u] = distTo[v] + w;
      edgeTo[u] = e;
      return true;
    }

    return false;
  }

  public Double distTo(int v) {
    validateVertex(v);

    return distTo[v];
  }

  private void validateVertex(int v) {
    int V = g.V();
    if (v < 0 || v >= V) {
      throw new IllegalArgumentException("vertex is not in graph");
    }
  }

  public boolean hasPathTo(int v) {
    validateVertex(v);

    if (v == start) {
      return true;
    }

    return edgeTo[v] != null;
  }

  public Iterable<WeightedEdge> pathTo(int v) {
    validateVertex(v);

    int u = v;
    List<WeightedEdge> p = new ArrayList<>();
    while (edgeTo[u] != null) {
      p.add(edgeTo[u]);
      u = edgeTo[u].other(u);
    }

    return p;
  }
}
