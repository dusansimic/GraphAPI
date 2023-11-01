package graphapi;

public class Algorithms {
  /**
   * Batagelj and Zaversnik's k-core decomposition algorithm.
   * 
   * @param g the graph
   * @return the array of shell indices of the vertices
   * @throws IllegalArgumentException if {@code g} is {@code null}
   */
  public static int[] kCoreDecompose(Graph g) {
    if (g == null) {
      throw new IllegalArgumentException("Graph cannot be null");
    }

    int n = g.V();
    int md = 0;
    int[] deg = new int[n];

    for (int v = 0; v < n; v++) {
      deg[v] = g.degree(v);
      if (deg[v] > md) {
        md = deg[v];
      }
    }

    int[] bin = new int[md + 1];
    for (int v = 0; v < n; v++) {
      bin[deg[v]]++;
    }

    int start = 0;
    for (int d = 0; d <= md; d++) {
      start += bin[d];
      bin[d] = start - bin[d];
    }

    int[] pos = new int[n];
    int[] vert = new int[n];
    for (int v = 0; v < n; v++) {
      pos[v] = bin[deg[v]];
      vert[pos[v]] = v;
      bin[deg[v]]++;
    }

    for (int d = md; d > 0; d--) {
      bin[d] = bin[d - 1];
    }
    bin[0] = 0;
    for (int i = 0; i < n; i++) {
      int v = vert[i];
      for (int u : g.adj(v)) {
        if (deg[u] > deg[v]) {
          int du = deg[u];
          int pu = pos[u];
          int pw = bin[du];
          int w = vert[pw];
          if (u != w) {
            pos[u] = pw;
            pos[w] = pu;
            vert[pu] = w;
            vert[pw] = u;
          }
          bin[du]++;
          deg[u]--;
        }
      }
    }

    return deg;
  }
}
