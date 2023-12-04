package graphapi;

public class WeightedEdge implements Comparable<WeightedEdge> {
  private final int v, w;
  private final double weight;

  public WeightedEdge(int v, int w, double weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public int either() {
    return v;
  }

  public int other(int u) {
    if (v == u) {
      return w;
    } else if (w == u) {
      return v;
    } else {
      throw new IllegalArgumentException("vertex not incident");
    }
  }

  public int compareTo(WeightedEdge that) {
    if (weight < that.weight)
      return -1;
    else if (weight > that.weight)
      return 1;
    else
      return 0;
  }

  public double weight() {
    return weight;
  }

  public String toString() {
    return String.format("[E %d-%d %f]", v, w, weight);
  }
}
