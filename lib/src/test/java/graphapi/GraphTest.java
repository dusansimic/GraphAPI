package graphapi;

import org.junit.Test;

public class GraphTest {
  @Test
  public void useEmptyConstructor() {
    Graph g = new Graph();
  }

  @Test
  public void useNonEmptyConstructor() {
    Graph g = new Graph(10);
  }
}
