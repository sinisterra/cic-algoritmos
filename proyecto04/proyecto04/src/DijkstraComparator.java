import java.util.Comparator;

public class DijkstraComparator implements Comparator<Nodo> {
  public int compare(Nodo a, Nodo b) {
    return a.d_distancia - b.d_distancia;
  }
}
