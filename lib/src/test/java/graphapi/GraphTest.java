package graphapi;

import org.junit.Test;

public class GraphTest {
  @Test
  public void useEmptyConstructor() {
    SimpleGraph g = new SimpleGraph();
  }

  @Test
  public void useNonEmptyConstructor() {
    SimpleGraph g = new SimpleGraph(10);
  }
}
