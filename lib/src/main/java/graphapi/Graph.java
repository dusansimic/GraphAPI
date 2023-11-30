package graphapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class Graph {
  private class IntegerSet extends HashSet<Integer> {
  }

  public class Edge {
    public final int v;
    public final int w;

    public Edge(int v, int w) {
      this.v = v;
      this.w = w;
    }
  }

  public class Pair<V, W> {
    public final V v;
    public final W w;

    public Pair(V v, W w) {
      this.v = v;
      this.w = w;
    }
  }

  private IntegerSet[] neighbours;
  private int edges;

  /**
   * Create a new graph with no vertices.
   */
  public Graph() {
    neighbours = new IntegerSet[0];
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

    neighbours = new IntegerSet[v];

    for (int i = 0; i < v; i++) {
      neighbours[i] = new IntegerSet();
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
   * Create a new graph from another graph.
   *
   * @param g Graph
   * @throws IllegalArgumentException if g is null
   */
  public Graph(Graph g) {
    this(g == null ? 0 : g.V());

    if (g == null) {
      throw new IllegalArgumentException("Graph cannot be null");
    }

    for (int i = 0; i < g.V(); i++) {
      for (int w : g.adj(i)) {
        addEdge(i, w);
      }
    }
  }

  public Iterable<Integer> vertices() {
    return new IntRange(0, V());
  }

  /**
   * Induce a subgraph by vertices.
   *
   * @param g        Graph
   * @param vertices Vertices to keep
   * @return Induced subgraph
   * @throws IllegalArgumentException if vertices are not present in the graph
   */
  public static Graph induceByVertices(Graph g, int[] vertices) {
    Graph newGraph = new Graph(g);

    for (int i = 0; i < vertices.length; i++) {
      if (vertices[i] < 0 || vertices[i] >= g.V()) {
        throw new IllegalArgumentException("Vertices must be present in graph");
      }
      newGraph.removeVertex(vertices[i] - i);
    }

    return newGraph;
  }

  /**
   * Induce a subgraph by edges.
   *
   * @param g     Graph
   * @param edges Edges to remove
   * @return Induced subgraph
   * @throws IllegalArgumentException if edges are not present in the graph
   *                                  or if edges are null
   */
  public static Graph induceByEdges(Graph g, Edge[] edges) {
    Graph newGraph = new Graph(g);

    for (Edge e : edges) {
      if (e == null) {
        throw new IllegalArgumentException("Edge cannot be null");
      }

      if (e.v < 0 || e.v >= g.V() || e.w < 0 || e.w >= g.V()) {
        throw new IllegalArgumentException("Vertices must be present in graph");
      }

      newGraph.removeEdge(e);
    }

    return newGraph;
  }

  /**
   * Create a k-core of a graph.
   *
   * @param g     Graph
   * @param cores Array of core numbers
   * @param k     Core number
   * @return Induced k-core
   */
  public static Graph induceKCore(Graph g, int[] cores, int k) {
    Graph newGraph = new Graph(g);

    for (int i = 0; i < cores.length; i++) {
      if (cores[i] < k) {
        newGraph.removeVertex(i);
      }
    }

    return newGraph;
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
    IntegerSet[] newNeighbours = new IntegerSet[neighbours.length + 1];

    for (int i = 0; i < neighbours.length; i++) {
      newNeighbours[i] = neighbours[i];
    }

    newNeighbours[neighbours.length] = new IntegerSet();
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

    IntegerSet[] newNeighbours = new IntegerSet[neighbours.length - 1];

    for (int i = 0; i < v; i++) {
      newNeighbours[i] = neighbours[i];
    }

    for (int i = v + 1; i < neighbours.length; i++) {
      newNeighbours[i - 1] = neighbours[i];
    }

    neighbours = newNeighbours;

    for (IntegerSet neighs : neighbours) {
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
  public boolean removeEdge(int v, int w) {
    validateVertex(v);
    validateVertex(w);

    if (neighbours[v].contains(w)) {
      neighbours[v].remove(w);
      neighbours[w].remove(v);
      edges--;
      return true;
    }

    return false;
  }

  /**
   * Remove an edge.
   *
   * @param e Edge
   * @throws IllegalArgumentException if e is null or if vertices are not present
   */
  public void removeEdge(Edge e) {
    if (e == null) {
      throw new IllegalArgumentException("Edge cannot be null");
    }

    if (e.v < 0 || e.v >= neighbours.length || e.w < 0 || e.w >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    removeEdge(e.v, e.w);
  }

  /**
   * Vertices adjacent to v.
   *
   * @param v Vertex
   * @return Iterable of vertices adjacent to v
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public Iterable<Integer> adj(int v) {
    validateVertex(v);

    return neighbours[v];
  }

  private void validateVertex(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertex must be present in graph");
    }
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
   * Check if there is a path between two vertices.
   *
   * @param v Vertex
   * @param w Vertex
   * @return True if there is a path between v and w, false otherwise
   * @throws IllegalArgumentException if v or w is not present in the graph
   */
  public boolean hasPath(int v, int w) {
    if (v < 0 || v >= neighbours.length || w < 0 || w >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    for (int x : component(v)) {
      if (x == w) {
        return true;
      }
    }

    return false;
  }

  /**
   * Find the shortest path between vertex v and w.
   *
   * @param v Vertex
   * @param w Vertex
   * @return Iterable of vertices in the path
   * @throws IllegalArgumentException if v or w is not present in the graph
   */
  public Iterable<Integer> path(int v, int w) {
    if (v < 0 || v >= neighbours.length || w < 0 || w >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    boolean[] marker = new boolean[neighbours.length];
    int[] edgeTo = new int[neighbours.length];
    int[] distTo = new int[neighbours.length];

    distTo[v] = 0;

    dfsMarker(v, v, marker, edgeTo, distTo);

    if (!marker[w]) {
      return null;
    }

    ArrayList<Integer> path = new ArrayList<>();

    for (int x = w; x != v;) {
      path.add(0, x);
      x = edgeTo[x];
    }

    path.add(0, v);

    return path;
  }

  /**
   * Find all the components in the graph.
   *
   * @return Iterable of components
   */
  public Iterable<Iterable<Integer>> components() {
    ArrayList<Iterable<Integer>> comps = new ArrayList<>();
    Bag<Integer> bag = new Bag<>();
    for (int i = 0; i < neighbours.length; i++) {
      bag.add(i);
    }

    while (!bag.isEmpty()) {
      int v = bag.pick();
      Iterable<Integer> comp = component(v);

      for (int w : comp) {
        bag.remove(w);
      }

      comps.add(comp);
    }

    return comps;
  }

  /**
   * Find the component which contains vertex v.
   *
   * @param v
   * @return Iterable of vertices in the component
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public Iterable<Integer> component(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    boolean[] marked = new boolean[neighbours.length];
    int[] edgeTo = new int[neighbours.length];
    int[] distTo = new int[neighbours.length];

    distTo[v] = 0;

    dfsMarker(v, v, marked, edgeTo, distTo);

    HashSet<Integer> comp = new HashSet<>();
    for (int i = 0; i < marked.length; i++) {
      if (marked[i]) {
        comp.add(i);
      }
    }

    return comp;
  }

  /**
   * Check if v is in a contour.
   *
   * @param v
   * @return True if v is in a contour, false otherwise
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public boolean hasContours(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    return dfsCountourFinder(v);
  }

  /**
   * DFS implementation specificaly used for finding contours which contain
   * vertex v.
   *
   * @param v Vertex
   * @return True if v is in a contour, false otherwise
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public boolean dfsCountourFinder(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    LinkedList<Integer> s = new LinkedList<>();
    boolean[] visited = new boolean[neighbours.length];
    visited[v] = true;

    for (int w : neighbours[v]) {
      s.push(w);
    }

    while (!s.isEmpty()) {
      int w = s.pop();
      visited[w] = true;

      for (int x : neighbours[w]) {
        if (!visited[x] && neighbours[v].contains(x)) {
          return true;
        }

        if (!visited[x]) {
          s.push(x);
        }
      }
    }

    return false;
  }

  /**
   * Use dfs to go over all vertices and mark them.
   *
   * @param v      Current vertex
   * @param w      Edge to vertex
   * @param marked Array of marked vertices
   * @param edgeTo Array of vertices that edge to the index vertex
   * @param distTo Array of distances from vertex v
   */
  private void dfsMarker(int v, int w, boolean[] marked, int[] edgeTo, int[] distTo) {
    if (marked[v]) {
      return;
    }

    marked[v] = true;
    edgeTo[v] = w;
    distTo[v] = distTo[w] + 1;

    for (Integer x : neighbours[v]) {
      dfsMarker(x, v, marked, edgeTo, distTo);
    }
  }

  /**
   * Find the shortest path between vertex v and all other vertices in the graph.
   *
   * @param v
   * @return Iterable of distances from vertex v
   * @throws IllegalArgumentException if v is not present in the graph
   */
  public Iterable<Integer> distances(int v) {
    if (v < 0 || v >= neighbours.length) {
      throw new IllegalArgumentException("Vertices must be present in graph");
    }

    Integer[] edgeTo = new Integer[neighbours.length];
    Integer[] distTo = new Integer[neighbours.length];

    bfsEdger(v, edgeTo, distTo);

    return Arrays.asList(distTo);
  }

  /**
   * Find distances to other vertices in graph from one vertex.
   *
   * @param v      Vertex
   * @param edgeTo Array of vertices which are the edge to the corresponding index
   * @param distTo Array of distances from vertex v
   */
  private void bfsEdger(int v, Integer[] edgeTo, Integer[] distTo) {
    LinkedList<Integer> q = new LinkedList<>();
    q.add(v);
    edgeTo[v] = -1;
    distTo[v] = 0;

    while (!q.isEmpty()) {
      v = q.removeFirst();

      for (int w : neighbours[v]) {
        if (edgeTo[w] == null) {
          edgeTo[w] = v;
          distTo[w] = distTo[v] + 1;

          q.add(w);
        }
      }
    }
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
