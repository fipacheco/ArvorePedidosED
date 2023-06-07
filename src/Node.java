public class Node {
    public Pedido pedido;
    public Node left;
    public Node right;

    public Node(Pedido pedido) {
        this.pedido = pedido;
        left = null;
        right = null;
    }

}
