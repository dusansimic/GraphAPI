package graphapi;

public class TSTSet<V> {
  private Node<V> root;

  private static class Node<V> {
    private V value;
    private char c;
    private Node<V> left;
    private Node<V> middle;
    private Node<V> right;

    public Node(char c) {
      this(c, null);
    }

    public Node(char c, V value) {
      this.c = c;
      this.value = value;
    }
  }

  private void validateKey(String key) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
  }

  public void put(String key, V value) {
    root = put(root, key, value, 0);
  }

  private Node<V> put(Node<V> x, String key, V value, int d) {
    char c = key.charAt(d);
    if (x == null) {
      x = new Node<V>(key.charAt(d));
    }

    if (c < x.c) {
      x.left = put(x.left, key, value, d);
    } else if (c > x.c) {
      x.right = put(x.right, key, value, d);
    } else if (d < key.length() - 1) {
      x.middle = put(x.middle, key, value, d+1);
    } else {
      x.value = value;
    }

    return x;
  }

  public V get(String key) {
    validateKey(key);
    Node<V> x = get(root, key, 0);
    return x == null ? null : x.value;
  }

  private Node<V> get(Node<V> x, String key, int d) {
    if (x == null) {
      return null;
    }

    char c = key.charAt(d);

    if (c < x.c) {
      return get(x.left, key, d);
    } else if (c > x.c) {
      return get(x.right, key, d);
    } else if (d < key.charAt(d)) {
      return get(x.middle, key, d+1);
    } else {
      return x;
    }
  }

  public boolean contains(String key) {
    return get(key) != null;
  }
}
