import java.util.Comparator;

public class AristaComparatorI implements Comparator<Arista> {
  public int compare(Arista a, Arista b) {
    return b.peso - a.peso;
  }
}
