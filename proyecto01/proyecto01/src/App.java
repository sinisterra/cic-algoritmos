import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Generando grafos...");

        for (int n : Arrays.asList(30, 100, 500)) {

            System.out.println(String.format("%d nodos", n));
            System.out.println(String.format("Erdos-Renyi con %d nodos", n));
            GeneradorGrafos.genErdosRenyi(n, (int) Math.round(n * 5), true, false)
                    .toGraphviz(String.format("gen_erdos_ren_%d", n));

            System.out.println(String.format("Gilbert con %d nodos", n));
            GeneradorGrafos.genGilbert(n, 0.2, false, false).toGraphviz(String.format("gen_gilbert_%d", n));

            System.out.println(String.format("Geográfico Simple con %d nodos", n));
            GeneradorGrafos.genGeografico(n, 0.33, false, false).toGraphviz(String.format("gen_geografico_%d", n));

            System.out.println(String.format("Barabasi-Albert con %d nodos", n));
            GeneradorGrafos.genBarabasiAlbert(n, 8, false, false).toGraphviz(String.format("gen_BarabasiAlbert_%d", n));
            System.out.println("\n");
        }

        System.out.println("Proceso finalizado.");
    }
}
