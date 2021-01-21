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
import java.util.Set;
import java.util.Arrays;

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

    public void addArista(Arista a) {
        aristas.put(a.id, a);
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

    public void grafoEstaConectado() {
        int nodos = this.nodos.size();

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

    public boolean grafoConectado() {
        Grafo tree = new Grafo(true, false);
        HashSet<Integer> memoria = new HashSet<>();

        this.DFSRec(0, tree, memoria);

        return memoria.equals(this.nodos.keySet());
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
        Grafo t = new Grafo(false, false);
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

        for (Nodo n : this.nodos.values()) {
            t.addNode(n);
            if (n.d_padre != null) {
                Arista a = this.getArista(n.id, n.d_padre.id);
                if (a != null) {
                    t.addArista(a);
                } else {
                    System.out.println(String.format("No hay arista entre %d y %d", n.id, n.d_padre.id));
                }
            }
        }

        return t;
    }

    public Grafo kruskalD() {

        Grafo t = new Grafo(false, false);

        // id_componente -> conjunto de componentes
        HashMap<Integer, HashSet<Integer>> componentes = new HashMap<Integer, HashSet<Integer>>();

        // id_nodo -> id_componente
        HashMap<Integer, Integer> nodo_pertenece_a_componente = new HashMap<Integer, Integer>();

        for (Nodo n : this.nodos.values()) {
            // crear el conjunto componente
            HashSet<Integer> conjunto_componente = new HashSet<Integer>();

            // meter el nodo al conjunto componente
            conjunto_componente.add(n.id);

            // guardar el conjunto componente
            componentes.put(n.id, conjunto_componente);
            nodo_pertenece_a_componente.put(n.id, n.id);
        }

        PriorityQueue<Arista> cola_aristas = new PriorityQueue<Arista>(1000, new AristaComparator());

        for (Arista a : this.aristas.values()) {
            cola_aristas.add(a);
        }

        while (!cola_aristas.isEmpty()) {
            Arista a = cola_aristas.poll();
            int id_source = a.source.id;
            int id_target = a.target.id;
            // System.out.println(String.format("%d -- %d [w= %d]", id_source, id_target,
            // a.peso));

            int cc_source = nodo_pertenece_a_componente.get(id_source);
            int cc_target = nodo_pertenece_a_componente.get(id_target);

            // System.out.println(String.format("%d en %d, %d en %d", id_source, cc_source,
            // id_target, cc_target));
            if (cc_source != cc_target) {
                // asigna a todas las instancias de cc_target el componente conectado de
                // cc_source
                HashSet<Integer> nuevo_componente_conectado = componentes.get(cc_source);
                nuevo_componente_conectado.addAll(componentes.get(cc_target));
                for (int n : nuevo_componente_conectado) {
                    nodo_pertenece_a_componente.put(n, cc_source);
                }

                // reemplazar en el hashmap el nuevo componente conectado construido
                componentes.put(cc_source, nuevo_componente_conectado);

                t.addNode(a.source);
                t.addNode(a.target);
                t.addArista(a);
            }
            // System.out.println(t.aristas);
        }

        int mst = 0;
        int pesos = 0;
        for (Arista a : t.aristas.values()) {
            mst += a.peso;
        }
        for (Arista a : aristas.values()) {
            pesos += a.peso;
        }

        System.out.println(String.format("Peso total: %d\nKruskal D -> MST: %d", pesos, mst));

        return t;
    }

    public Grafo quitarArista(Arista arista) {
        this.aristas.remove(arista.id);
        return this;
    }

    public Grafo kruskalI() {
        Grafo t = new Grafo(false, false);

        PriorityQueue<Arista> cola_aristas = new PriorityQueue<Arista>(100, new AristaComparatorI());
        // agregar todos los nodos y aristas al grafo
        for (Arista a : this.aristas.values()) {
            t.addNode(a.source);
            t.addNode(a.target);
            t.addArista(a);
            cola_aristas.add(a);
        }

        while (!cola_aristas.isEmpty()) {
            Arista a = cola_aristas.poll();
            Grafo grafoSinArista = t.quitarArista(a);
            // quitar arista
            if (grafoSinArista.grafoConectado()) {
                t = grafoSinArista;
            } else {
                grafoSinArista.addArista(a);
            }
        }

        int mst = 0;
        for (Arista a : t.aristas.values()) {
            mst += a.peso;
        }

        System.out.println(String.format("Kruskal I -> MST: %d", mst));

        return t;

    }

    public Grafo prim() {
        Grafo t = new Grafo(false, false);
        PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>(1000, new DijkstraComparator());

        Nodo origen = this.getNode(0);
        origen.d_distancia = 0;

        for (Nodo n : this.nodos.values()) {
            cola.add(n);
        }

        while (!cola.isEmpty()) {
            Nodo u = cola.poll();

            for (int vecino_id : this.idsConnectedTo(u)) {
                Nodo v = this.getNode(vecino_id);
                Arista a = this.getArista(u.id, v.id);

                if (cola.contains(v) && (v.d_distancia > a.peso)) {
                    // quitar a v de la cola
                    cola.remove(v);
                    // actualizar el padre y la distancia
                    v.d_padre = u;
                    v.d_distancia = a.peso;
                    // meter v a la cola de nuevo
                    cola.add(v);
                }
            }
        }

        // copiar la información etiquetada a un nuevo grafo
        for (Nodo n : this.nodos.values()) {
            t.addNode(n);
            if (n.d_padre != null) {
                Arista a = this.getArista(n.id, n.d_padre.id);
                if (a != null) {
                    t.addArista(a);
                } else {
                    System.out.println(String.format("No hay arista entre %d y %d", n.id, n.d_padre.id));
                }
            }
        }

        // agregar el nodo 0 al grafo

        // reportar el peso del MST
        int mst = 0;
        for (Arista a : t.aristas.values()) {
            mst += a.peso;
        }

        System.out.println(String.format("Prim -> MST: %d\n", mst));

        return t;
    }
}
