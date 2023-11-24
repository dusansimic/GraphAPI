package graphapi;

public class DirectedDFS {
  private DiGraph g;
  private boolean[] marked;
  private int count;

  /**
   * Find nodes that are reachable from a specified node.
   * 
   * @param g Directed graph in which to find reachable nodes
   * @param v Source node
   */
  public DirectedDFS(DiGraph g, int v) {
    this.g = g;
    marked = new boolean[g.V()];
    count = 0;
    findReachable(v);
  }

  /**
   * Find nodes that are reachable from a specified set of nodes.
   * 
   * @param g       Directed graph in which to find reachable nodes
   * @param sources Source nodes
   */
  public DirectedDFS(DiGraph g, Iterable<Integer> sources) {
    this.g = g;
    marked = new boolean[g.V()];
    count = 0;
    for (int s : sources) {
      if (!marked[s]) {
        findReachable(s);
      }
    }
  }

  private void findReachable(int v) {
    count++;
    marked[v] = true;

    for (int w : g.adj(v)) {
      if (!marked[w]) {
        findReachable(w);
      }
    }
  }

  public int count() {
    return count;
  }

  public boolean marked(int v) {
    return marked[v];
  }
}
