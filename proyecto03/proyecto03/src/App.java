import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        for (int n : Arrays.asList(30)) {

            // System.out.println(String.format("%d nodos", n));
            // System.out.println(String.format("Erdos-Renyi con %d nodos", n));
            // Grafo g1 = GeneradorGrafos.genErdosRenyi(n, (int) Math.round(n * 5), false,
            // false);
            // g1.toGraphviz(String.format("gen_erdos_ren_%d", n), false);
            // g1.dijkstra(0);
            // g1.toGraphviz(String.format("gen_erdos_ren_%d_dijkstra", n), true);

            System.out.println(String.format("Gilbert con %d nodos", n));
            Grafo g2 = GeneradorGrafos.genGilbert(n, 0.10, false, false);
            g2.toGraphviz(String.format("gen_gilbert_%d", n), false);
            g2.dijkstra(0);
            g2.toGraphviz(String.format("gen_gilbert_%d_dijkstra", n), true);

            // System.out.println(String.format("Geográfico Simple con %d nodos", n));
            // Grafo g3 = GeneradorGrafos.genGeografico(n, 0.3, false, false);
            // g3.toGraphviz(String.format("gen_geografico_%d", n), false);
            // g3.dijkstra(0);
            // g3.toGraphviz(String.format("gen_geografico_%d_dijkstra", n), true);

            // System.out.println(String.format("Barabasi-Albert con %d nodos", n));
            // Grafo g4 = GeneradorGrafos.genBarabasiAlbert(n, 5, false, false);
            // g4.toGraphviz(String.format("gen_BarabasiAlbert_%d", n), false);
            // g4.dijkstra(0);
            // g4.toGraphviz(String.format("gen_BarabasiAlbert_%d_dijkstra", n), true);

            System.out.println("\n");
        }

    }
}
