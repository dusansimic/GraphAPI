package graphapi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class In {
  public class Pair<V, W> {
    public final V v;
    public final W w;

    public Pair(V v, W w) {
      this.v = v;
      this.w = w;
    }
  }

  BufferedReader br;

  /**
   * Create a new input stream from file.
   *
   * @param filename File name
   * @throws FileNotFoundException if the file cannot be opened
   */
  public In(String filename) throws FileNotFoundException {
    br = new BufferedReader(new FileReader(filename));
  }

  /**
   * Read an integer from the input stream.
   * 
   * @return Integer
   * @throws NumberFormatException if the file does not contain a valid integer
   * @throws IOException           if the file cannot be read
   */
  public int readInt() throws NumberFormatException, IOException {
    return Integer.parseInt(br.readLine());
  }

  /**
   * Read a pair of integers from the input stream.
   * 
   * @return Pair of integers
   * @throws NumberFormatException if the file does not contain a valid integer
   * @throws IOException           if the file cannot be read
   */
  public Pair<Integer, Integer> readIntPair() throws NumberFormatException, IOException {
    String[] line = br.readLine().split(" ");
    return new Pair<Integer, Integer>(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
  }

  /**
   * Close the input stream.
   * 
   * @throws IOException
   */
  public void end() throws IOException {
    br.close();
  }
}
