package graphapi;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class InTest {
  @Test
  public void useConstructor_noException() throws FileNotFoundException {
    In in = new In("src/test/resources/graphapi/tinyg.txt");
    in.end();
  }

  @Test
  public void useConstructor_exceptionFileNotFound() {
    Assert.assertThrows(FileNotFoundException.class, () -> new In("/graphapi/doesnotexist.txt"));
  }

  @Test
  public void readInt_noException() throws NumberFormatException, IOException {
    In in = new In("src/test/resources/graphapi/readInt.txt");
    in.readInt();
    in.end();
  }

  @Test
  public void readInt_numberFormatException() throws FileNotFoundException {
    In in = new In("src/test/resources/graphapi/readInt_numberFormatException.txt");
    Assert.assertThrows(NumberFormatException.class, () -> in.readInt());
    in.end();
  }

  @Test
  public void readIntPair_noException() throws NumberFormatException, IOException {
    In in = new In("src/test/resources/graphapi/readIntPair.txt");
    in.readIntPair();
    in.end();
  }

  @Test
  public void readIntPair_numberFormatException() throws IOException {
    In in = new In("src/test/resources/graphapi/readIntPair_numberFormatException.txt");
    Assert.assertThrows(NumberFormatException.class, () -> in.readIntPair());
    in.end();
  }

  @Test
  public void readTinyG_noException() throws NumberFormatException, IOException {
    In in = new In("src/test/resources/graphapi/tinyg.txt");
    in.readInt();
    int edges = in.readInt();
    for (int i = 0; i < edges; i++) {
      in.readIntPair();
    }
    in.end();
  }

  @Test
  public void readMediumG_noException() throws NumberFormatException, IOException {
    In in = new In("src/test/resources/graphapi/mediumg.txt");
    in.readInt();
    int edges = in.readInt();
    for (int i = 0; i < edges; i++) {
      in.readIntPair();
    }
    in.end();
  }
}
