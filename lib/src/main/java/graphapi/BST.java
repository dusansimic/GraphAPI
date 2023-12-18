package graphapi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingDeque;

import org.checkerframework.checker.units.qual.t;

public class BST<K extends Comparable<K>, V> implements Map<K, V> {

  private static class Node<K extends Comparable<K>, V> implements Entry<K, V> {
    private K key;
    private V value;
    private Node<K, V> left;
    private Node<K, V> right;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public Node(K key, V value, Node<K, V> left, Node<K, V> right) {
      this(key, value);
      this.left = left;
      this.right = right;
    }

    @Override
    public String toString() {
      return "Node [key=" + key + ", value=" + value + ", left=" + left + ", right=" + right + "]";
    }

    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return value;
    }

    @Override
    public V setValue(V value) {
      V oldValue = this.value;
      this.value = value;
      return oldValue;
    }

  }

  private Node<K, V> root;
  private int size;

  public BST() {
    root = null;
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  private K validateKey(Object key) {
    try {
      return (K) key;
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("key must be of type K");
    }
  }

  private V validateValue(Object value) {
    try {
      return (V) value;
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("value must be of type V");
    }
  }

  @Override
  public boolean containsKey(Object key) {
    final K k = validateKey(key);
    Node<K, V> current = root;

    while (current != null && !current.key.equals(k)) {
      current = current.key.compareTo(k) > 0 ? current.left : current.right;
    }

    return current != null;
  }

  @Override
  public boolean containsValue(Object value) {
    final V v = validateValue(value);

    List<Node<K, V>> q = new LinkedList<>();

    q.add(root);

    while (!q.isEmpty()) {
      Node<K, V> current = q.remove(0);
      if (current == null) {
        continue;
      }
      if (current.value.equals(v)) {
        return true;
      }

      if (current.left != null)
        q.add(current.left);
      if (current.right != null)
        q.add(current.right);
    }

    return false;
  }

  @Override
  public V get(Object key) {
    final K k = validateKey(key);
    Node<K, V> current = root;

    while (current != null && !current.key.equals(k)) {
      current = current.key.compareTo(k) > 0 ? current.left : current.right;
    }

    return current != null ? current.value : null;
  }

  @Override
  public V put(K key, V value) {
    final K k = validateKey(key);
    final V v = validateValue(value);
    V oldValue = null;

    if (root == null) {
      root = new Node<K, V>(k, v);
      size++;
      return oldValue;
    }

    Node<K, V> current = root;

    while (true) {
      if (current.key.compareTo(k) == 0) {
        oldValue = current.value;
        current.value = v;
        return oldValue;
      } else if (current.key.compareTo(k) < 0) {
        if (current.right == null) {
          current.right = new Node<K, V>(k, v);
          size++;
          return oldValue;
        }
        current = current.right;
      } else {
        if (current.left == null) {
          current.left = new Node<K, V>(k, v);
          size++;
          return oldValue;
        }
        current = current.left;
      }
    }
  }

  @Override
  public V remove(Object key) {
    K k = validateKey(key);

    Node<K, V> previous = null;
    boolean previousLeft = false;
    Node<K, V> current = root;

    while (current != null) {
      if (current.key.compareTo(k) == 0) {
        break;
      } else if (current.key.compareTo(k) < 0) {
        previous = current;
        current = current.right;
        previousLeft = false;
      } else {
        previous = current;
        current = current.left;
        previousLeft = true;
      }
    }

    if (current == null) {
      return null;
    }

    if (current.left == null && current.right == null) {
      if (current == root) {
        root = null;
        return current.value;
      }

      if (previousLeft)
        previous.left = null;
      else
        previous.right = null;
    }

    {
      Node<K, V> replacer;
      if (current.left == null ^ current.right == null) {
        replacer = current.left != null ? current.left : current.right;

        if (previousLeft) {
          previous.left = replacer;
        } else {
          previous.right = replacer;
        }
      }
    }

    {
    }
    return null;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'putAll'");
  }

  @Override
  public void clear() {
    root = null;
    size = 0;
  }

  @Override
  public Set<K> keySet() {
    Set<K> keys = new TreeSet<>();

    List<Node<K, V>> q = new LinkedList<>();

    q.add(root);

    while (!q.isEmpty()) {
      Node<K, V> current = q.remove(0);
      if (current == null) {
        continue;
      }
      keys.add(current.key);
    }

    return keys;
  }

  @Override
  public Collection<V> values() {
    List<V> values = new LinkedList<>();

    List<Node<K, V>> q = new LinkedList<>();

    q.add(root);

    while (!q.isEmpty()) {
      Node<K, V> current = q.remove(0);
      if (current == null) {
        continue;
      }
      values.add(current.value);
    }

    return values;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> entries = new TreeSet<>();

    List<Node<K, V>> q = new LinkedList<>();

    q.add(root);

    while (!q.isEmpty()) {
      Node<K, V> current = q.remove(0);
      if (current == null) {
        continue;
      }
      entries.add(new Node<K, V>(current.key, current.value));
    }

    return entries;
  }

}
