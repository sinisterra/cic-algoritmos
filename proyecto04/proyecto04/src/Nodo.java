public class Nodo {
  public int id;

  // parámetros para el algoritmo de Dijkstra
  public Nodo d_padre;
  public boolean d_visto;
  public int d_distancia;

  public Nodo(int id) {
    this.id = id;

    // parámetros para el algoritmo de Dijkstra
    this.d_padre = null;
    this.d_visto = false;
    this.d_distancia = Integer.MAX_VALUE;
  }

  @Override
  public String toString() {

    return String.format("Nodo {id = %d padre=%s d_distancia=%d}", this.id,
        this.d_padre == null ? null : this.d_padre.id, this.d_distancia);
  }
}
