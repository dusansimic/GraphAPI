package graphapi;

public interface Graph {
  public int V();

  public int E();

  public Iterable<Integer> adj(int v);

  public Iterable<Integer> vertices();

  public int degree(int v);

  public void addEdge(int v, int w);

  public boolean removeEdge(int v, int w);
}
