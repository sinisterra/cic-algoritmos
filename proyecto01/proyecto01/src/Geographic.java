import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Geographic extends Grafo {
  private int n;
  private double r;
  private boolean dirigido = false;
  private boolean auto = false;

  public Geographic(int n, double r, boolean dirigido, boolean auto) {
    this.n = n;
    this.r = r;
    this.dirigido = dirigido;
    this.auto = auto;

    this.genGeografico();
  }

  public void genGeografico() {
    HashMap<Integer, double[]> coordenadas = new HashMap<>();
    Random random = new Random();

    for (int i = 0; i < n; i++) {
      this.addNode(new Nodo(i));
      double[] coord = new double[] { random.nextDouble(), random.nextDouble() };
      coordenadas.put(i, coord);
    }

    for (int i = 0; i < n; i++) {
      // seleccionar la coordenada del nodo origen
      double[] coordOrigen = coordenadas.get(i);
      for (int j = 0; j < n; j++) {
        // evitar aristas paralelas
        if (i != j && this.getArista(j, i) == null) {
          double[] coordDestino = coordenadas.get(j);
          if (distanciaMenorQueR(coordOrigen, coordDestino)) {
            this.addEdge(new Nodo(i), new Nodo(j));
          }
        }
      }
    }
  }

  private boolean distanciaMenorQueR(double[] coord1, double[] coord2) {
    return this.distanciaEuclideana(coord1, coord2) <= r;
  }

  private double distanciaEuclideana(double[] coord1, double[] coord2) {
    return Math.sqrt(Math.pow((coord2[0] - coord1[0]), 2) + Math.pow((coord2[1] - coord1[1]), 2));
  }

}
