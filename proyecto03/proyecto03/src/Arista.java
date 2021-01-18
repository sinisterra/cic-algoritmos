import java.util.Random;

public class Arista {

    public String id;
    public Nodo source;
    public Nodo target;
    public int peso;

    public Arista(Nodo source, Nodo target) {
        this.source = source;
        this.target = target;
        this.id = String.format("%d_%d", source.id, target.id);

        this.peso = new Random().nextInt(100) + 1;
    }
}
