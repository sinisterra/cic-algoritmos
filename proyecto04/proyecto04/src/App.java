import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        for (int n : Arrays.asList(30, 200)) {

            System.out.println(String.format("%d nodos", n));
            System.out.println(String.format("Erdos-Renyi con %d nodos", n));
            Grafo g1 = GeneradorGrafos.genErdosRenyi(n, (int) Math.round(n * 5), false, false);
            g1.toGraphviz(String.format("gen_erdos_ren_%d", n), false);
            g1.kruskalD().toGraphviz(String.format("gen_erdos_ren_%d_d_kruskal", n), false);
            g1.kruskalI().toGraphviz(String.format("gen_erdos_ren_%d_i_kruskal", n), false);
            g1.prim().toGraphviz(String.format("gen_erdos_ren_%d_prim", n), false);

            System.out.println(String.format("Gilbert con %d nodos", n));
            Grafo g2 = GeneradorGrafos.genGilbert(n, 0.10, false, false);
            g2.toGraphviz(String.format("gen_gilbert_%d", n), false);
            g2.kruskalD().toGraphviz(String.format("gen_gilbert_%d_d_kruskal", n), false);
            g2.kruskalI().toGraphviz(String.format("gen_gilbert_%d_i_kruskal", n), false);
            g2.prim().toGraphviz(String.format("gen_gilbert_%d_prim", n), false);

            System.out.println(String.format("Geográfico Simple con %d nodos", n));
            Grafo g3 = GeneradorGrafos.genGeografico(n, 0.3, false, false);
            g3.toGraphviz(String.format("gen_geografico_%d", n), false);
            g3.kruskalD().toGraphviz(String.format("gen_geografico_%d_d_kruskal", n), false);
            g3.kruskalI().toGraphviz(String.format("gen_geografico_%d_i_kruskal", n), false);
            g3.prim().toGraphviz(String.format("gen_geografico_%d_prim", n), false);

            System.out.println(String.format("Barabasi-Albert con %d nodos", n));
            Grafo g4 = GeneradorGrafos.genBarabasiAlbert(n, 5, false, false);
            g4.toGraphviz(String.format("gen_BarabasiAlbert_%d", n), false);

            g4.kruskalD().toGraphviz(String.format("gen_BarabasiAlbert_%d_d_kruskal", n), false);
            g4.kruskalI().toGraphviz(String.format("gen_BarabasiAlbert_%d_i_kruskal", n), false);
            g4.prim().toGraphviz(String.format("gen_BarabasiAlbert_%d_prim", n), false);

            System.out.println("\n");
        }

    }
}
