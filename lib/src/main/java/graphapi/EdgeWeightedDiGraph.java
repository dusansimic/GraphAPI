package graphapi;

import java.io.IOException;

public class EdgeWeightedDiGraph {
  private class WeightedArcBag extends Bag<WeightedArc> {
  }

  private WeightedArcBag[] adj;
  private WeightedArcBag arcs;

  public EdgeWeightedDiGraph(int V) {
    adj = new WeightedArcBag[V];
    for (int i = 0; i < V; i++) {
      adj[i] = new WeightedArcBag();
    }
    arcs = new WeightedArcBag();
  }

  public EdgeWeightedDiGraph(In in) throws IOException {
    this(in.readInt());
    int e = in.readInt();

    for (int i : new IntRange(e)) {
      In.WeightedEdgeTriple arc = in.readWeightedEdge();
      addArc(new WeightedArc(arc.first(), arc.second(), arc.third()));
    }
  }

  public Iterable<WeightedArc> arcs() {
    return arcs.items();
  }

  public int V() {
    return adj.length;
  }

  public int A() {
    return arcs.size();
  }

  public void addArc(WeightedArc a) {
    if (a == null) {
      throw new IllegalArgumentException("arc argument cannot be null");
    }

    int v = a.from();
    validateVertex(v);
    validateVertex(a.to());
    adj[v].add(a);
  }

  private void validateVertex(int v) {
    if (v < 0 || v >= adj.length) {
      throw new IllegalArgumentException("vetrex must be present in graph");
    }
  }

  public Iterable<WeightedArc> adj(int v) {
    validateVertex(v);
    return adj[v].items();
  }

  public Iterable<Integer> vertices() {
    return new IntRange(0, V());
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("[EdgeWeightedDiGraph\n");
    for (int v : vertices()) {
      s.append(String.format("\t[%d", v));
      for (WeightedArc a : adj[v].items()) {
        s.append(String.format(" %s", a));
      }
      s.append("]\n");
    }
    s.append("]");
    return s.toString();
  }
}
