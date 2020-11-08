import java.util.Random;

public class Gilbert extends Grafo {
  private int n; // total de nodos
  private double p; // probabilidad de que se cree una arista entre dos nodos
  private boolean dirigido;

  public Gilbert(int n, double p, boolean dirigido, boolean auto) {
    super(dirigido, auto);

    this.n = n;
    this.p = p;
    this.dirigido = dirigido;

    this.genGilbert();
  }

  private void genGilbert() {
    Random random = new Random();

    for (int i = 0; i < this.n; i++) {
      this.addNode(new Nodo(i));
    }

    for (int i = 0; i < this.n; i++) {
      for (int j = 0; j < this.n; j++) {

        if (i != j) {
          /*
           * hay que validar que esta arista no exista ya; es decir, si ya hay una j -> i
           * entonces no debe poder crearse i -> con el fin de evitar que hayan aristas
           * paralelas
           */

          if (this.getArista(j, i) == null) {

            // generar un número aleatorio entre 0 y 1
            double aleatorio = random.nextDouble();
            if (aleatorio <= p) {
              this.addEdge(this.getNode(i), this.getNode(j));
            }
          }
        }
      }
    }

  }

}
