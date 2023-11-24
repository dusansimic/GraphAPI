package graphapi;

public class BipartiteGraph {
  private Graph g;

  public BipartiteGraph(Graph g) {
    this.g = g;
  }

  public boolean validateBipartite() {
    Boolean[] colors = new Boolean[g.V()];

    for (int v : g.vertices()) {
      if (colors[v] == null) {
        if (!dfs(v, false, colors)) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean dfs(int v, boolean color, Boolean[] colors) {
    colors[v] = color;

    for (int w : g.adj(v)) {
      if (colors[v].equals(colors[w])) {
        return false;
      }

      if (colors[w] == null) {
        return dfs(w, !color, colors);
      }
    }

    return true;
  }
}
