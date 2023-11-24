package graphapi;

import java.util.HashSet;

public class Bag<T> extends HashSet<T> {
  public T pick() {
    if (isEmpty()) {
      return null;
    }

    T t = iterator().next();
    remove(t);
    return t;
  }
}
