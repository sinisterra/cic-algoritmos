import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public abstract class Grafo {
    private HashMap<Integer, Nodo> nodos = new HashMap<Integer, Nodo>();
    private HashMap<String, Arista> aristas = new HashMap<String, Arista>();
    private boolean dirigido = false;
    private boolean auto = false;

    public Grafo() {

    }

    public Grafo(boolean dirigido, boolean auto) {
        this.dirigido = dirigido;
        this.auto = auto;
    }

    public Grafo addEdge(Nodo source, Nodo target) {
        Arista arista = new Arista(source, target);
        aristas.put(arista.id, arista);
        return this;
    }

    public Grafo addNode(Nodo nodo) {
        nodos.put(nodo.id, nodo);
        return this;
    }

    public Nodo getNode(int id) {
        return nodos.get(id);
    }

    public List<Integer> idsConnectedTo(Nodo node) {
        List<Integer> acc = new ArrayList<Integer>();

        for (Arista a : aristas.values()) {
            if (a.source.id == node.id) {
                acc.add(a.target.id);
            }

            if (!dirigido) {
                if (a.target.id == node.id) {
                    acc.add(a.source.id);
                }
            }

        }

        return new ArrayList<Integer>(new HashSet<Integer>(acc));
    }

    public Arista getArista(int sourceId, int targetId) {
        String llaveArista = String.format("%d_%d", sourceId, targetId);

        return aristas.get(llaveArista);
    }

    public int calcularGrado(int idNodo) {
        int totalAristas = 0;

        for (Arista a : aristas.values()) {
            if (a.source.id == idNodo || a.target.id == idNodo) {
                totalAristas++;
            }
        }

        return totalAristas;
    }

    public int totalAristas() {
        return aristas.size();
    }

    public void toGraphviz(String nombreArchivo) throws IOException {

        String graphvizConectorArista = dirigido ? "->" : "--";
        String graphvizTipoGrafo = dirigido ? "digraph" : "graph";

        Path pathArchivo = Path.of(String.format("%s.gv", nombreArchivo));

        StringBuilder archivo = new StringBuilder()
                .append(String.format("%s %s {\n", graphvizTipoGrafo, nombreArchivo));

        // nodos.forEach((nodoId, n) -> archivo.append(String.format(" nodo_%d;\n",
        // nodoId)));

        aristas.forEach((aristaId, a) -> archivo
                .append(String.format("  nodo_%d %s nodo_%d;\n", a.source.id, graphvizConectorArista, a.target.id)));

        archivo.append("}");

        Files.writeString(pathArchivo, archivo.toString());

    }
}
