import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        for (int n : Arrays.asList(30, 100, 500)) {
            GeneradorGrafos.genErdosRenyi(n, (int) Math.round(n * 2), true, false)
                    .toGraphviz(String.format("gen_erdos_ren_%d", n));

            GeneradorGrafos.genGilbert(n, 0.05, false, false).toGraphviz(String.format("gen_gilbert_%d", n));

            GeneradorGrafos.genGeografico(n, 0.33, false, false).toGraphviz(String.format("gen_geografico_%d", n));

            GeneradorGrafos.genBarabasiAlbert(n, 5, false, false).toGraphviz(String.format("gen_BarabasiAlbert_%d", n));
        }
    }
}
