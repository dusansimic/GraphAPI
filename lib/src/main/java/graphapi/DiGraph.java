package graphapi;

import java.io.IOException;
import java.util.HashSet;

public class DiGraph {
  private class IntegerSet extends HashSet<Integer> {
  }

  private IntegerSet[] neighbours;
  private int edges;

  public DiGraph(int v) {
    neighbours = new IntegerSet[v];
    for (int i = 0; i < v; i++) {
      neighbours[i] = new IntegerSet();
    }
    edges = 0;
  }

  public DiGraph(In in) throws NumberFormatException, IOException {
    this(in.readInt());
    int e = in.readInt();
    edges = 0;

    for (int i : new IntRange(e)) {
      In.Pair<Integer, Integer> edge = in.readIntPair();
      addEdge(edge.v, edge.w);
    }
  }

  public DiGraph clone() {
    DiGraph g = new DiGraph(V());

    for (int v : vertices()) {
      for (int w : adj(v)) {
        g.addEdge(v, w);
      }
    }

    return g;
  }

  public int V() {
    return neighbours.length;
  }

  public int E() {
    return edges;
  }

  public Iterable<Integer> adj(int v) {
    return neighbours[v];
  }

  public Iterable<Integer> vertices() {
    return new IntRange(0, neighbours.length);
  }

  public void addEdge(int v, int w) {
    validateVertex(v);
    validateVertex(w);

    neighbours[v].add(w);
    edges++;
  }

  private void validateVertex(int v) {
    if (v < 0 || v >= V()) {
      throw new IllegalArgumentException("Vertex has to exist in the graph");
    }
  }

  public boolean removeEdge(int v, int w) {
    validateVertex(v);
    validateVertex(w);

    if (neighbours[v].contains(w)) {
      neighbours[v].remove(w);
      return true;
    }

    return false;
  }

  public Iterable<Iterable<Integer>> weakComponents() {
    return getSimpleGraph().components();
  }

  public Iterable<Integer> weakComponent(int v) {
    return getSimpleGraph().component(v);
  }

  public Iterable<Integer> strongComponent(int v) {
    return null;
  }

  public Graph getSimpleGraph() {
    Graph g = new Graph(V());

    for (int v : vertices()) {
      for (int w : neighbours[v]) {
        g.addEdge(v, w);
      }
    }

    return g;
  }

  public int degree(int v) {
    validateVertex(v);
    return neighbours[v].size();
  }

  public int indegree(int v) {
    validateVertex(v);
    int count = 0;
    for (IntegerSet neighs : neighbours) {
      if (neighs.contains(v)) {
        count++;
      }
    }
    return count;
  }

  public int outdegree(int v) {
    validateVertex(v);
    return neighbours[v].size();
  }

  public DiGraph reverse() {
    DiGraph g = new DiGraph(V());

    for (int v : vertices()) {
      for (int w : adj(v)) {
        g.addEdge(w, v);
      }
    }

    return g;
  }
}
