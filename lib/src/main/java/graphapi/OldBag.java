package graphapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OldBag<T> extends HashSet<T> {
  public T pick() {
    if (isEmpty()) {
      return null;
    }

    T t = iterator().next();
    remove(t);
    return t;
  }
}

class Bag<T> {
  private List<T> bag;

  public Bag() {
    bag = new ArrayList<>();
  }

  public void add(T item) {
    if (item == null) {
      throw new IllegalArgumentException("item cannot be null");
    }

    bag.add(item);
  }

  public T pick() {
    return bag.isEmpty() ? null : bag.remove(0);
  }

  public T peek() {
    return bag.isEmpty() ? null : bag.get(0);
  }

  public boolean contains(T t) {
    if (t == null) {
      throw new IllegalArgumentException("no null items in bag");
    }

    return bag.contains(t);
  }

  public Iterable<T> items() {
    return bag;
  }

  public int size() {
    return bag.size();
  }
}

class IntegerBag extends OldBag<Integer> {
}
