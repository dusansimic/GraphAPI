package graphapi;

public class UndirectedDFS {
  private SimpleGraph g;
  private boolean[] marked;
  private int[] edgeTo;
  private int count;

  public UndirectedDFS(SimpleGraph g, int v) {
    this.g = g;
    marked = new boolean[g.V()];
    edgeTo = new int[g.V()];
    count = 0;
    edgeTo[v] = -1;
    findReachable(v);
  }

  private void findReachable(int v) {
    count++;
    marked[v] = true;

    for (int w : g.adj(v)) {
      if (!marked[w]) {
        edgeTo[w] = v;
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

  public int edgeTo(int v) {
    return edgeTo[v];
  }
}
