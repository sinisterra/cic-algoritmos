public class Arista {

    public String id;
    public Nodo source;
    public Nodo target;

    public Arista(Nodo source, Nodo target) {
        this.source = source;
        this.target = target;
        this.id = String.format("%d_%d", source.id, target.id);
    }
}
