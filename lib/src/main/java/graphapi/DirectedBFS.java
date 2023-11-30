package graphapi;

import java.util.LinkedList;

public class DirectedBFS {
  private DiGraph g;
  private boolean[] marked;
  private int count;

  public DirectedBFS(DiGraph g, int v) {
    this.g = g;
    marked = new boolean[g.V()];
    count = 0;
    findReachable(v);
  }

  private void findReachable(int v) {
    LinkedList<Integer> q = new LinkedList<>();
    q.addLast(v);

    while (q.size() != 0) {
      int w = q.removeFirst();
      if (marked[w]) {
        continue;
      }

      count++;
      marked[w] = true;

      for (int x : g.adj(w)) {
        q.addLast(x);
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
