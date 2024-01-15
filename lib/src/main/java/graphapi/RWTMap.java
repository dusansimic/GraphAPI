package graphapi;

public class RWTMap<V> {
  private static final int R = 256;
  private Node<V> root;

  private static class Node<V> {
    private V value;
    private Node[] next = new Node[R];

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
      x = new Node<V>(value);
    }

    if (d == key.length()) {
      x.value = value;
      return x;
    }

    char c = key.charAt(d);
    x.next[c] = put(x.next[c], key, value, d+1);
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
    return get(x.next[c], key, d+1);
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
    x.next[c] = remove(x.next[c], key, d+1);

    if (validateLinksNull(x)) {
      return null;
    } else {
      return x;
    }
  }
}
