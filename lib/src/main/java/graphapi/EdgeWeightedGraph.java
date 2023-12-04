package graphapi;

import java.io.IOException;

public class EdgeWeightedGraph {
  private class WeightedEdgeBag extends Bag<WeightedEdge> {
  }

  private WeightedEdgeBag[] adj;
  private WeightedEdgeBag edges;

  public EdgeWeightedGraph(int V) {
    adj = new WeightedEdgeBag[V];
    for (int i = 0; i < V; i++) {
      adj[i] = new WeightedEdgeBag();
    }
    edges = new WeightedEdgeBag();
  }

  public EdgeWeightedGraph(In in) throws IOException {
    this(in.readInt());
    int e = in.readInt();

    for (int i : new IntRange(e)) {
      In.WeightedEdgeTriple edge = in.readWeightedEdge();
      addEdge(new WeightedEdge(
          edge.first(),
          edge.second(),
          edge.third()));
    }
  }

  public Iterable<WeightedEdge> edges() {
    return edges.items();
  }

  public int V() {
    return adj.length;
  }

  public int E() {
    return edges.size();
  }

  public void addEdge(WeightedEdge e) {
    if (e == null) {
      throw new IllegalArgumentException("edge argument cannot be null");
    }

    int v = e.either();
    validateVertex(v);
    int w = e.other(v);
    validateVertex(w);

    adj[v].add(e);
    adj[w].add(e);
    edges.add(e);
  }

  private void validateVertex(int v) {
    if (v < 0 || v >= adj.length) {
      throw new IllegalArgumentException("vetrex must be present in graph");
    }
  }

  public Iterable<WeightedEdge> adj(int v) {
    validateVertex(v);
    return adj[v].items();
  }

  public Iterable<Integer> vertices() {
    return new IntRange(0, V());
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("[EdgeWeightedGraph\n");
    for (int v : vertices()) {
      s.append(String.format("\t[%d", v));
      for (WeightedEdge e : adj[v].items()) {
        s.append(String.format(" %s", e));
      }
      s.append("]\n");
    }
    s.append("]");
    return s.toString();
  }
}
