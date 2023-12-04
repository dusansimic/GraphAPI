package graphapi;

public class WeightedArc implements Comparable<WeightedArc> {
  private final int v, w;
  private final double weight;

  public WeightedArc(int v, int w, double weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public int from() {
    return v;
  }

  public int to() {
    return w;
  }

  public int compareTo(WeightedArc that) {
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
    return String.format("[A %d->%d %f]", v, w, weight);
  }
}
