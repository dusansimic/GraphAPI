package graphapi;

public class TransitiveClosure {
  private DiGraph g;
  private Boolean[][] reachable;
  private boolean[] marked;

  public TransitiveClosure(DiGraph g) {
    this.g = g;
    reachable = new Boolean[g.V()][g.V()];
  }

  public boolean reachable(int v, int w) {
    if (reachable[v][w] != null) {
      return reachable[v][w];
    }

    marked = new boolean[g.V()];
    marked[v] = true;

    boolean isReachable = reach(v, w);
    if (isReachable) {
      reachable[v][w] = isReachable;
    }

    return isReachable;
  }

  private boolean reach(int v, int w) {
    if (v == w) {
      return true;
    }

    for (int u : g.adj(v)) {
      if (!marked[u] && reach(u, w)) {
        return true;
      }
    }

    return false;
  }
}
