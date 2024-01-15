package graphapi;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

public class RWTMap<V> {
  private static final int R = 256;
  private Node<V> root;

  private static class Node<V> {
    private V value;
    private Node[] next = new Node[R];

    public Node() {
      this(null);
    }

    public Node(V value) {
      this.value = value;
    }
  }

  private void validateKey(String key) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
  }

  public void put(String key, V value) {
    validateKey(key);
    root = put(root, key, value, 0);
  }

  private Node<V> put(Node<V> x, String key, V value, int d) {
    if (x == null) {
      x = new Node<V>();
    }

    if (d == key.length()) {
      x.value = value;
      return x;
    }

    char c = key.charAt(d);
    x.next[c] = put(x.next[c], key, value, d + 1);
    return x;
  }

  public void update(String key, Function<V, V> function) {
    validateKey(key);
    root = update(root, key, function, 0);
  }

  private Node<V> update(Node<V> x, String key, Function<V, V> function, int d) {
    if (x == null) {
      x = new Node<V>();
    }

    if (d == key.length()) {
      x.value = function.apply(x.value);
      return x;
    }

    char c = key.charAt(d);
    x.next[c] = update(x.next[c], key, function, d + 1);
    return x;
  }

  public V get(String key) {
    validateKey(key);
    Node<V> x = get(root, key, 0);

    if (x == null) {
      return null;
    } else {
      return x.value;
    }
  }

  private Node<V> get(Node<V> x, String key, int d) {
    if (x == null) {
      return null;
    }

    if (d == key.length()) {
      return x;
    }

    char c = key.charAt(d);
    return get(x.next[c], key, d + 1);
  }

  public boolean contains(String key) {
    return get(key) != null;
  }

  public void remove(String key) {
    validateKey(key);
    root = remove(root, key, 0);
  }

  private boolean validateLinksNull(Node<V> x) {
    for (Node<V> next : x.next) {
      if (next != null) {
        return false;
      }
    }
    return true;
  }

  private Node<V> remove(Node<V> x, String key, int d) {
    if (x == null) {
      return null;
    }

    if (d == key.length()) {
      if (validateLinksNull(x)) {
        return null;
      }

      x.value = null;
      return x;
    }

    char c = key.charAt(d);
    x.next[c] = remove(x.next[c], key, d + 1);

    if (validateLinksNull(x)) {
      return null;
    } else {
      return x;
    }
  }

  public Iterable<String> keys() {
    Queue<String> q = new LinkedList<>();
    StringBuilder sb = new StringBuilder();
    collect(root, sb, q);
    return q;
  }

  private void collect(Node<V> x, StringBuilder prefix, Queue<String> q) {
    if (x == null) {
      return;
    }

    if (x.value != null) {
      q.add(prefix.toString());
    }

    for (char c = 0; c < R; c++) {
      prefix.append(c);
      collect(x.next[c], prefix, q);
      prefix.deleteCharAt(prefix.length() - 1);
    }
  }

  public Iterable<String> keysWithPrefix(String prefix) {
    Queue<String> q = new LinkedList<>();
    Node<V> x = get(root, prefix, 0);
    StringBuilder sb = new StringBuilder(prefix);
    collect(x, sb, q);
    return q;
  }

  public Iterable<String> keysThatMatch(String pattern) {
    Queue<String> q = new LinkedList<>();
    StringBuilder sb = new StringBuilder();
    collect(root, sb, pattern, q);
    return q;
  }

  private void collect(Node<V> x, StringBuilder prefix, String pattern, Queue<String> q) {
    if (x == null) {
      return;
    }

    int d = prefix.length();

    if (d == pattern.length()) {
      if (x.value != null) {
        q.add(prefix.toString());
      } else {
        return;
      }
    }

    char next = pattern.charAt(d);

    for (char c = 0; c < R; c++) {
      if (next == '.' || next == c) {
        prefix.append(c);
        collect(x.next[c], prefix, pattern, q);
        prefix.deleteCharAt(prefix.length() - 1);
      }
    }
  }

  public String longestPrefixOf(String query) {
    int length = search(root, query, 0, 0);
    return query.substring(0, length);
  }

  private int search(Node<V> x, String query, int d, int length) {
    if (x == null) {
      return length;
    }

    if (x.value != null) {
      length = d;
    }

    if (d == query.length()) {
      return length;
    }

    char c = query.charAt(d);

    return search(x.next[c], query, d + 1, length);
  }
}
