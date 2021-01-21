import java.util.Comparator;

public class AristaComparator implements Comparator<Arista> {
  public int compare(Arista a, Arista b) {
    return a.peso - b.peso;
  }
}
