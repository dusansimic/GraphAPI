package graphapi;

import java.util.Iterator;

public class IntRange implements Iterable<Integer> {
  private class IntRangeIterator implements Iterator<Integer> {
    private final int beginInclusive, endExclusive;
    private int offset;

    public IntRangeIterator(int beginInclusive, int endExclusive) {
      this.beginInclusive = beginInclusive;
      this.endExclusive = endExclusive;
      offset = 0;
    }

    @Override
    public boolean hasNext() {
      return beginInclusive + offset < endExclusive;
    }

    @Override
    public Integer next() {
      int next = beginInclusive + offset;
      offset++;
      return next;
    }
  }

  private final int beginInclusive, endExclusive;

  public IntRange(int endExclusive) {
    this.beginInclusive = 0;
    this.endExclusive = endExclusive;
  }

  public IntRange(int beginInclusive, int endExclusive) {
    this.beginInclusive = beginInclusive;
    this.endExclusive = endExclusive;
  }

  @Override
  public Iterator<Integer> iterator() {
    return new IntRangeIterator(beginInclusive, endExclusive);
  }
}
