import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Grafo {
    public HashMap<Integer, Nodo> nodos = new HashMap<Integer, Nodo>();
    private HashMap<String, Arista> aristas = new HashMap<String, Arista>();
    public boolean dirigido = false;
    public boolean auto = false;

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
        String llaveAristaI = String.format("%d_%d", targetId, sourceId);
        Arista a = aristas.get(llaveArista);
        Arista b = aristas.get(llaveAristaI);

        if (a != null) {
            return a;
        } else {
            return b;
        }

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

    public void toGraphviz(String nombreArchivo, boolean dijkstra) throws IOException {

        String graphvizConectorArista = dirigido ? "->" : "--";
        String graphvizTipoGrafo = dirigido ? "digraph" : "graph";

        Path pathArchivo = Path.of(String.format("%s.gv", nombreArchivo));

        StringBuilder archivo = new StringBuilder()
                .append(String.format("%s %s {\n", graphvizTipoGrafo, nombreArchivo));

        // nodos.forEach((nodoId, n) -> archivo.append(String.format(" nodo_%d;\n",
        // nodoId)));
        if (dijkstra == false) {
            aristas.forEach(
                    (aristaId, a) -> archivo.append(String.format("  nodo_%d %s nodo_%d [label=%d weight=%d];\n",
                            a.source.id, graphvizConectorArista, a.target.id, a.peso, a.peso)));
        }

        if (dijkstra == true) {
            for (int nodo_id : nodos.keySet()) {
                Nodo n = this.getNode(nodo_id);
                if (n.d_padre != null) {
                    archivo.append(String.format(" \"nodo_%d (%d)\" %s \"nodo_%d (%d)\" [label=%d weight=%d]\n",
                            n.d_padre.id, n.d_padre.d_distancia, graphvizConectorArista, n.id, n.d_distancia,
                            this.getArista(n.d_padre.id, n.id).peso, this.getArista(n.d_padre.id, n.id).peso));
                }
            }
        }

        archivo.append("}");

        Files.writeString(pathArchivo, archivo.toString());

    }

    // sobrecarga de la función para que regrese el
    // grafo tomando 0 como raíz
    public Grafo toBFSTree() {
        return this.toBFSTree(0);
    }

    public Grafo toBFSTree(int raizId) {
        Grafo tree = new Grafo();
        tree.dirigido = true;

        // seleccionar una raíz
        Nodo raiz = this.getNode(raizId);
        // System.out.println(raiz);

        Queue<Integer> frontera = new LinkedList<Integer>();
        HashSet<Integer> memoria = new HashSet<Integer>();

        // for (Nodo n : this.nodos.values()) {
        // frontera.add(n.id);
        // }
        tree.addNode(raiz);
        frontera.add(raizId);

        while (!frontera.isEmpty()) {
            // seleccionar el primer elemento en la frontera
            Integer s = frontera.poll();

            // buscar el nodo y sus vecinos
            Nodo source = this.getNode(s);
            if (!tree.nodos.containsValue(source)) {
                tree.addNode(source);
            }

            List<Integer> vecinos = this.idsConnectedTo(source);
            // omite todos los nodos que ya visité
            vecinos.removeAll(memoria);

            // por cada vecino del origen que aún no he visto...
            for (Integer v : vecinos) {
                Nodo vecino = this.getNode(v);

                /*
                 * si es un nodo que aún tengo pendiente por visitar, agregarlo a los pendientes
                 * por visitar, dibujar un enlace
                 */

                if (!frontera.contains(v)) {
                    frontera.add(v);

                    if (!tree.nodos.containsValue(vecino)) {
                        tree.addNode(vecino);
                    }

                    tree.addEdge(source, vecino);
                }

                // si es un nodo que no está en el árbol, crearlo y dibujar un enlace
                if (!tree.nodos.containsValue(vecino)) {
                    tree.addNode(vecino);
                    tree.addEdge(source, vecino);
                }
            }

            // ya que terminé con este nodo, agregarlo a la memoria
            memoria.add(s);

        }

        return tree;
    }

    public Grafo toDFSTreeIterative() {
        return this.toDFSTreeIterative(0);
    }

    public Grafo toDFSTreeRecursive() {
        return this.toDFSTreeRecursive(0);
    }

    private void DFSRec(int raiz, Grafo dfs, HashSet<Integer> memoria) {
        Nodo source = this.getNode(raiz);
        List<Integer> vecinos = this.idsConnectedTo(source);

        memoria.add(raiz);

        for (Integer v : vecinos) {
            Nodo target = this.getNode(v);
            if (!memoria.contains(v)) {
                if (!dfs.nodos.containsKey(raiz)) {
                    dfs.addNode(source);
                }
                if (!dfs.nodos.containsKey(v)) {
                    dfs.addNode(target);
                }
                dfs.addEdge(source, target);
                DFSRec(v, dfs, memoria);
            }
        }
    }

    public Grafo toDFSTreeRecursive(int raizId) {
        Grafo tree = new Grafo(true, false);
        HashSet<Integer> memoria = new HashSet<>();

        this.DFSRec(raizId, tree, memoria);

        return tree;
    }

    public Grafo toDFSTreeIterative(int raizId) {
        Grafo tree = new Grafo(true, false);

        Stack<Integer> frontera = new Stack<Integer>();
        HashSet<Integer> memoria = new HashSet<Integer>();
        // nodo
        HashMap<Integer, Integer> terminal = new HashMap<Integer, Integer>();

        frontera.add(raizId);

        while (!frontera.isEmpty()) {
            // System.out.println(frontera);
            Integer s = frontera.pop();
            Nodo source = this.getNode(s);

            List<Integer> vecinos = this.idsConnectedTo(source);
            tree.addNode(source);

            for (Integer v : vecinos) {
                if (!memoria.contains(v)) {
                    frontera.add(v);
                    terminal.put(v, s);
                }
            }

            memoria.add(s);
        }

        for (Integer s : terminal.keySet()) {
            Nodo source = this.getNode(s);
            Nodo target = this.getNode(terminal.get(s));

            if (!tree.nodos.containsValue(source)) {
                tree.addNode(source);
            }
            if (!tree.nodos.containsValue(target)) {
                tree.addNode(target);
            }

            tree.addEdge(target, source);
        }

        return tree;
    }

    public Grafo dijkstra(int nodo) {
        Grafo dtree = new Grafo(false, false);
        PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>(1000, new DijkstraComparator());

        Nodo nodoFuente = this.getNode(nodo);
        // marcar en el nodo fuente que tiene una distancia de cero
        nodoFuente.d_distancia = 0;
        cola.add(nodoFuente);

        while (!cola.isEmpty()) {
            Nodo u = cola.poll();
            u.d_visto = true;
            List<Integer> vecinos = this.idsConnectedTo(u);
            for (int v_id : vecinos) {
                Nodo v = this.getNode(v_id);

                if (!v.d_visto) {
                    Arista a = this.getArista(u.id, v.id);
                    if (v.d_distancia > u.d_distancia + a.peso) {
                        v.d_distancia = u.d_distancia + a.peso;
                        v.d_padre = u;
                        cola.add(v);
                    }
                }
            }
        }

        return dtree;
    }
}
