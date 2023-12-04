package graphapi;

import java.util.ArrayList;
import java.util.List;

public abstract class IndexPQ<K extends Comparable<K>> {
  public static enum Order {
    NATURAL(1),
    REVERSED(-1);

    private int coef;

    Order(int coef) {
      this.coef = coef;
    }

    int coef() {
      return coef;
    }
  }

  private Order order;
  private List<Integer> iq;
  private List<K> kq;

  public IndexPQ(Order order) {
    this.order = order;
    iq = new ArrayList<>();
    kq = new ArrayList<>();
  }

  public boolean isEmpty() {
    return iq.isEmpty();
  }

  public void enqueue(int index, K key) {
    for (int i = 0; i < kq.size(); i++) {
      if (order.coef() * key.compareTo(kq.get(i)) < 0) {
        iq.add(i, index);
        kq.add(i, key);
        return;
      }
    }
    iq.add(iq.size(), index);
    kq.add(kq.size(), key);
  }

  public int dequeue() {
    if (iq.isEmpty()) {
      throw new IndexOutOfBoundsException("priority queue is empty");
    }

    kq.remove(0);
    return iq.remove(0);
  }

  public void changeKey(int index, K key) {
    int oldPlace = iq.indexOf(index);
    if (oldPlace == -1) {
      throw new IllegalArgumentException("index doesn't exists");
    }

    K oldKey = kq.get(oldPlace);
    if (oldKey.compareTo(key) == 0) {
      return;
    }

    int newPlace;
    if (oldKey.compareTo(key) > 0) {
      newPlace = binSearch(0, oldPlace - 1, key, kq);
    } else {
      newPlace = binSearch(oldPlace + 1, kq.size() - 1, key, kq);
    }

    iq.remove(oldPlace);
    kq.remove(oldPlace);

    iq.add(newPlace, index);
    kq.add(newPlace, key);
  }

  public boolean contains(int i) {
    return binSearch(0, iq.size() - 1, i, iq) != -1;
  }

  private <T extends Comparable<T>> int binSearch(int l, int r, T t, List<T> ls) {
    while (l <= r) {
      int m = l + (r - l) / 2;

      if (ls.get(m).compareTo(t) == 0) {
        return m;
      } else if (ls.get(m).compareTo(t) < 0) {
        l = m + 1;
      } else {
        r = m - 1;
      }
    }

    return -1;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("[PriorityQueue");
    for (int i = 0; i < iq.size(); i++) {
      sb.append(String.format(" %d (%s)", iq.get(i), kq.get(i).toString()));
      if (i != iq.size() - 1) {
        sb.append(",");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
