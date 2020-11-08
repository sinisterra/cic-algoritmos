import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ErdosRenyi extends Grafo {
  private int n;
  private int m;

  public ErdosRenyi(int n, int m, boolean dirigido, boolean auto) {
    super(dirigido, auto);

    this.n = n;
    this.m = m;

    this.genErdosRenyi();
  }

  private void genErdosRenyi() {
    Grafo g = this;

    Random random = new Random();

    List<Integer> nodosOrigen = new ArrayList<>();
    // generar todos los nodos del grafo
    for (int i = 0; i < n; i++) {
      g.addNode(new Nodo(i));
      nodosOrigen.add(i);
    }

    // generar los pares
    for (int j = 0; j < m; j++) {

      // elige un nodo entre los nodos disponibles
      int nodoOrigen = nodosOrigen.get(random.nextInt(nodosOrigen.size() - 1));

      // construir una nueva lista con los nodos posibles con los que se podría
      // conectar el nodo inicial.

      // copiar la lista
      List<Integer> nodosDisponibles = new ArrayList<>(nodosOrigen);

      // buscar todos los nodos que están conectados con el nodoOrigen
      List<Integer> nodosProhibidos = g.idsConnectedTo(g.getNode(nodoOrigen));

      // quitar el id del nodo destino
      nodosProhibidos.add(nodoOrigen);

      // quitar los nodos prohibidos de la lista de nodos disponibles
      for (int np : nodosProhibidos) {
        nodosDisponibles.removeIf(v -> v == np);
      }

      // seleccionar un nuevo nodo destino entre los nodos disponibles
      // primero seleccionar un índice
      int indiceNodoDestino = random.nextInt(nodosDisponibles.size() - 1);
      // seleccionar el nodo en el índice
      int nodoDestino = nodosDisponibles.get(indiceNodoDestino);

      g.addEdge(g.getNode(nodoOrigen), g.getNode(nodoDestino));

    }
  }

}
