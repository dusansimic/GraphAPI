package graphapi;

public class IndexMinPQ<K extends Comparable<K>> extends IndexPQ<K> {
  public IndexMinPQ() {
    super(IndexPQ.Order.NATURAL);
  }
}
