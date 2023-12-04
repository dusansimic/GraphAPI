package graphapi;

import java.util.ArrayList;
import java.util.List;

public class DijkstraDirectedSP {
  private EdgeWeightedDiGraph g;
  private int start;
  private Double[] distTo;
  private WeightedArc[] edgeTo;

  public DijkstraDirectedSP(EdgeWeightedDiGraph g, int u) {
    this.g = g;
    validateVertex(u);
    start = u;
    distTo = new Double[g.V()];
    edgeTo = new WeightedArc[g.V()];
    for (int v : g.vertices()) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }
    find(u);
  }

  private void validateVertex(int v) {
    int V = g.V();
    if (v < 0 || v >= V) {
      throw new IllegalArgumentException("vertex is not in graph");
    }
  }

  private void find(int u) {
    distTo[u] = 0.0;

    IndexMinPQ<Double> pq = new IndexMinPQ<>();
    pq.enqueue(u, distTo[u]);

    while (!pq.isEmpty()) {
      int v = pq.dequeue();
      for (WeightedArc a : g.adj(v)) {
        if (relax(a)) {
          int w = a.to();
          if (pq.contains(w)) {
            pq.changeKey(w, a.weight());
          } else {
            pq.enqueue(w, a.weight());
          }
        }
      }
    }
  }

  private boolean relax(WeightedArc a) {
    int v = a.from();
    int w = a.to();
    double weight = a.weight();
    if (distTo[w] > distTo[v] + weight) {
      distTo[w] = distTo[v] + weight;
      edgeTo[w] = a;
      return true;
    }
    return false;
  }

  public double distTo(int v) {
    validateVertex(v);
    return distTo[v];
  }

  public boolean hasPathTo(int v) {
    validateVertex(v);
    if (v == start) {
      return true;
    }
    return edgeTo[v] != null;
  }

  public Iterable<WeightedArc> pathTo(int v) {
    validateVertex(v);

    List<WeightedArc> p = new ArrayList<>();
    while (edgeTo[v] != null) {
      p.add(edgeTo[v]);
      v = edgeTo[v].from();
    }

    return p;
  }
}
