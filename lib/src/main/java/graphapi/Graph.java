package graphapi;

import java.io.IOException;
import java.util.ArrayList;

public class Graph {
  private class IntegerList extends ArrayList<Integer> {
  }

  private IntegerList[] neighbours;
  private int edges;

  /**
   * Create a new graph with no vertices.
   */
  public Graph() {
    neighbours = new IntegerList[0];
  }

  /**
   * Create a new graph with v vertices and no edges.
   * 
   * @param v Number of vertices
   * @throws IllegalArgumentException if v is negative
   */
  public Graph(int v) {
    if (v < 0) {
      throw new IllegalArgumentException("Number of vertices must be non-negative");
    }

    neighbours = new IntegerList[v];

    for (int i = 0; i < v; i++) {
      neighbours[i] = new IntegerList();
    }

    edges = 0;
  }

  /**
   * Create a new graph from input stream.
   * 
   * @param in Input stream
   * @throws NumberFormatException if the file does not contain a valid integer
   * @throws IOException           if the file cannot be read
   */
  public Graph(In in) throws NumberFormatException, IOException {
    this(in.readInt());
    int e = in.readInt();
    edges = 0;

    for (int i = 0; i < e; i++) {
      In.Pair<Integer, Integer> edge = in.readIntPair();
      addEdge(edge.v, edge.w);
      edges++;
    }
  }

  /**
   * Number of vertices in the graph.
   * 
   * @return Number of vertices
   */
  public int V() {
    return neighbours.length;
  }

  /**
   * Number of edges in the graph.
   * 
   * @return Number of edges
   */
  public int E() {
    return edges;
  }

  /**
   * Add a new vertex to the graph.
   */
  public void addVertex() {
    IntegerList[] newNeighbours = new IntegerList[neighbours.length + 1];

    for (int i = 0; i < neighbours.length; i++) {
      newNeighbours[i] = neighbours[i];
    }

    newNeighbours[neighbours.length] = new IntegerList();
    neighbours = newNeighbours;
  }

  /**
   * Remove a vertex from the graph.
   * 
   * @param v Vertex to remove
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public void removeVertex(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertex must be present in graph");
    }

    edges -= neighbours[v].size();

    IntegerList[] newNeighbours = new IntegerList[neighbours.length - 1];

    for (int i = 0; i < v; i++) {
      newNeighbours[i] = neighbours[i];
    }

    for (int i = v + 1; i < neighbours.length; i++) {
      newNeighbours[i - 1] = neighbours[i];
    }

    neighbours = newNeighbours;

    for (IntegerList neighs : neighbours) {
      neighs.remove((Integer) v);
    }
  }

  /**
   * Add an edge between vertices v and w.
   * 
   * @param v Vertex
   * @param w Vertex
   * @throws IllegalArgumentException if v or w is not present in the graph
   */
  public void addEdge(int v, int w) {
    if (v < 0 || v >= neighbours.length || w < 0 || w >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    neighbours[v].add(w);
    neighbours[w].add(v);
    edges++;
  }

  /**
   * Remove an edge between vertices v and w.
   * 
   * @param v Vertex
   * @param w Vertex
   * @throws IllegalArgumentException if v or w is not present in the graph
   */
  public void removeEdge(int v, int w) {
    if (v < 0 || v >= neighbours.length || w < 0 || w >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    neighbours[v].remove(w);
    neighbours[w].remove(v);
    edges--;
  }

  /**
   * Vertices adjacent to v.
   * 
   * @param v Vertex
   * @return Iterable of vertices adjacent to v
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public Iterable<Integer> adj(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    return neighbours[v];
  }

  /**
   * Degree of vertex v.
   * 
   * @param v Vertex
   * @return Degree of vertex v
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public int degree(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    return neighbours[v].size();
  }

  /**
   * String representation of the graph.
   * 
   * @return String representation of the graph
   */
  public String toString() {
    StringBuilder s = new StringBuilder();

    s.append(V() + " vertices, " + E() + " edges\n");

    for (int v = 0; v < V(); v++) {
      s.append(v + ": ");

      for (int w : neighbours[v]) {
        s.append(w + " ");
      }

      s.append("\n");
    }

    return s.toString();
  }
}
