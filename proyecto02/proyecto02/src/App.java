import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {

        for (int n : Arrays.asList(30, 100, 500)) {

            System.out.println(String.format("%d nodos", n));
            System.out.println(String.format("Erdos-Renyi con %d nodos", n));
            Grafo g1 = GeneradorGrafos.genErdosRenyi(n, (int) Math.round(n * 5), false, false);
            g1.toGraphviz(String.format("gen_erdos_ren_%d", n));
            g1.toBFSTree().toGraphviz(String.format("gen_erdos_ren_%d_bfs", n));
            g1.toDFSTreeIterative().toGraphviz(String.format("gen_erdos_ren_%d_dfs_i", n));
            g1.toDFSTreeRecursive().toGraphviz(String.format("gen_erdos_ren_%d_dfs_r", n));

            System.out.println(String.format("Gilbert con %d nodos", n));
            Grafo g2 = GeneradorGrafos.genGilbert(n, 0.05, false, false);
            g2.toGraphviz(String.format("gen_gilbert_%d", n));
            g2.toBFSTree().toGraphviz(String.format("gen_gilbert_%d_bfs", n));
            g2.toDFSTreeIterative().toGraphviz(String.format("gen_gilbert_%d_dfs_i", n));
            g2.toDFSTreeRecursive().toGraphviz(String.format("gen_gilbert_%d_dfs_r", n));

            System.out.println(String.format("Geográfico Simple con %d nodos", n));
            Grafo g3 = GeneradorGrafos.genGeografico(n, 0.3, false, false);
            g3.toGraphviz(String.format("gen_geografico_%d", n));
            g3.toBFSTree().toGraphviz(String.format("gen_geografico_%d_bfs", n));
            g3.toDFSTreeIterative().toGraphviz(String.format("gen_geografico_%d_dfs_i", n));
            g3.toDFSTreeRecursive().toGraphviz(String.format("gen_geografico_%d_dfs_r", n));

            System.out.println(String.format("Barabasi-Albert con %d nodos", n));
            Grafo g4 = GeneradorGrafos.genBarabasiAlbert(n, 5, false, false);
            g4.toGraphviz(String.format("gen_BarabasiAlbert_%d", n));
            g4.toBFSTree().toGraphviz(String.format("gen_BarabasiAlbert_%d_bfs", n));
            g4.toDFSTreeIterative().toGraphviz(String.format("gen_BarabasiAlbert_%d_dfs_i", n));
            g4.toDFSTreeRecursive().toGraphviz(String.format("gen_BarabasiAlbert_%d_dfs_r", n));

            System.out.println("\n");
        }

    }
}
