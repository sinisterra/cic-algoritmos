import java.util.Random;

public class Arista {

    public String id;
    public Nodo source;
    public Nodo target;

    public Arista(Nodo source, Nodo target) {
        this.source = source;
        this.target = target;
        this.id = String.format("%d_%d", source.id, target.id);

        Random rand;
        this.peso = rand.nextInt(1, 100);
    }
}
