package graphapi;

public class IndexMaxPQ<K extends Comparable<K>> extends IndexPQ<K> {
  public IndexMaxPQ() {
    super(IndexPQ.Order.REVERSED);
  }
}
