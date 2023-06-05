public class PedidoTree {
    private Node root;

    public PedidoTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void adicionarPedido(Pedido pedido) {
        Node newNode = new Node(pedido);

        if (root == null) {
            root = newNode;
        } else {
            Node current = root;
            Node parent;

            while (true) {
                parent = current;

                if (pedido.getData().before(current.pedido.getData())) {
                    current = current.left;

                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }
                } else {
                    current = current.right;

                    if (current == null) {
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    public Node buscarPedido(Node root, int id) {
        if (root == null || root.pedido.getId() == id) {
            return root;
        }

        if (id < root.pedido.getId()) {
            return buscarPedido(root.left, id);
        }

        return buscarPedido(root.right, id);
    }

    public void visualizarTodasAsVendas() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.println(root.pedido.toString());
            inOrderTraversal(root.right);
        }
    }

    public void inOrderTraversalWithLocation() {
        inOrderTraversalRecWithLocation(root, "");
    }

    private void inOrderTraversalRecWithLocation(Node root, String location) {
        if (root != null) {
            inOrderTraversalRecWithLocation(root.left, location + "(" + root.pedido.getId() + ") [ESQUERDA] ");
            System.out.println(root.pedido.toString() + " " + location);
            inOrderTraversalRecWithLocation(root.right, location + "(" + root.pedido.getId() + ") [DIREITA] ");
        }
    }

    public boolean removerPedido(int id) {
        root = removerPedidoRec(root, id);
        return root != null;
    }

    private Node removerPedidoRec(Node root, int id) {
        if (root == null) {
            return null;
        }

        if (id < root.pedido.getId()) {
            root.left = removerPedidoRec(root.left, id);
        } else if (id > root.pedido.getId()) {
            root.right = removerPedidoRec(root.right, id);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            Node sucessor = encontrarSucessor(root.right);
            root.pedido = sucessor.pedido;
            root.right = removerPedidoRec(root.right, sucessor.pedido.getId());
        }

        return root;
    }

    private Node encontrarSucessor(Node node) {
        Node atual = node;
        while (atual.left != null) {
            atual = atual.left;
        }
        return atual;
    }
}
