public class GeneradorGrafos {

    public static Grafo genErdosRenyi(int n, int m, boolean dirigido, boolean auto) {
        return new ErdosRenyi(n, m, dirigido, auto);
    }

    public static Grafo genGilbert(int n, double p, boolean dirigido, boolean auto) {
        return new Gilbert(n, p, dirigido, auto);
    }

    public static Grafo genGeografico(int n, double r, boolean dirigido, boolean auto) {
        return new Geographic(n, r, dirigido, auto);
    }

    public static Grafo genBarabasiAlbert(int n, double d, boolean dirigido, boolean auto) {
        return new BarabasiAlbert(n, d, dirigido, auto);
    }
}
