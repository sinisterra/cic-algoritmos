import java.util.Random;

public class BarabasiAlbert extends Grafo {
  private int n;
  private double d;
  private boolean dirigido = false;
  private boolean auto = false;

  public BarabasiAlbert(int n, double d, boolean dirigido, boolean auto) {
    this.n = n;
    this.d = d;
    this.dirigido = dirigido;
    this.auto = auto;

    this.genBarabasiAlbert();
  }

  private void genBarabasiAlbert() {
    Random random = new Random();

    for (int i = 0; i < this.n; i++) {
      this.addNode(new Nodo(i));
    }

    // for (int i = 0; i < this.d; i++) {
    // for (int j = 0; j < this.d; j++) {
    // if (i != j) {
    // if (this.getArista(j, i) == null) {
    // this.addEdge(this.getNode(i), this.getNode(j));
    // }
    // }
    // }
    // }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int gradoI = this.calcularGrado(i);
        int gradoJ = this.calcularGrado(j);

        if (i != j && this.getArista(j, i) == null && gradoI < d && gradoJ < d) {
          double aleatorio = random.nextDouble();

          double p = 1 - (gradoI / d);

          if (aleatorio <= p) {
            this.addEdge(this.getNode(i), this.getNode(j));
          }

        }
      }
    }
  }

}
