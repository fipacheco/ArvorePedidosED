import java.util.Stack;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class PedidoTree {
    private Node root;

    public PedidoTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }
    // Adiciona um pedido à árvore
    public void adicionarPedido(Pedido pedido) {
        Node newNode = new Node(pedido);

        if (root == null) {
            // Se a árvore está vazia, o novo nó se torna a raiz
            root = newNode;
        } else {
            Node current = root;
            Node parent;

            while (true) {
                parent = current;

                if (pedido.getData().before(current.pedido.getData())) {
                    // Se a data do pedido eh anterior a data do no atual, vai para a esquerda
                    current = current.left;

                    if (current == null) {
                        // Se o nó a esquerda eh nulo, insere o novo no ali
                        parent.left = newNode;
                        return;
                    }
                } else {
                    // Se a data do pedido eh posterior ou igual a data do no atual, vai para a direita

                    current = current.right;

                    if (current == null) {
                        // Se o no a direita eh nulo, insere o novo nó ali
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

     // busca pelo ID
    public Node buscarPedido(Node root, int id) {
        if (root == null || root.pedido.getId() == id) {
            return root;
        }

        if (id < root.pedido.getId()) {
            // Se o ID buscado eh menor, busca no galho a esquerda
            return buscarPedido(root.left, id);
        }
        // Se o ID buscado eh maior, busca no galho a direita

        return buscarPedido(root.right, id);
    }
    public boolean existePedido(int id) {
        return encontrarPedido(root, id) != null;
    }

    private Node encontrarPedido(Node root, int id) {
        if (root == null || root.pedido.getId() == id) {
            return root;
        }

        if (id < root.pedido.getId()) {
            return encontrarPedido(root.left, id);
        } else {
            return encontrarPedido(root.right, id);
        }
    }
    public void visualizarTodasAsVendas() {
        // mostra as vendas em ordem crescente
        inOrderTraversal(root);
    }
    // percorre a arvore em ordem e imprimir cada pedido.
    private void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.println(root.pedido.toString());
            inOrderTraversal(root.right);
        }
    }
    // percorre a arvore em ordem e imprimir cada pedido junto com a localizacao de cada no

    public void inOrderTraversalWithLocation() {

        inOrderTraversalRecWithLocation(root, "");
    }

    private void inOrderTraversalRecWithLocation(Node root, String location) {
        if (root != null) {
            inOrderTraversalRecWithLocation(root.left, location + "(" + root.pedido.getId() + ") [ESQUERDA] ");
            System.out.println(root.pedido.toString() + " " + location);
            inOrderTraversalRecWithLocation(root.right, location +  "(" + root.pedido.getId() + ") [DIREITA] ");
        }
    }

    // impressao de pedidos por ordem decrescente de valor.
    public void visualizarVendasPorValor() {
        List<Pedido> pedidos = new ArrayList<>();
        inOrderTraversalToList(root, pedidos);

        pedidos.sort(Comparator.comparingDouble(Pedido::getValor).reversed());

        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toString());
        }
    }

    private void inOrderTraversalToList(Node root, List<Pedido> pedidos) {
        if (root != null) {
            inOrderTraversalToList(root.left, pedidos);
            pedidos.add(root.pedido);
            inOrderTraversalToList(root.right, pedidos);
        }
    }

    // remove o pedido com o ID especificado da árvore
    public boolean removerPedido(int id) {
        ResultadoRemocao resultado = new ResultadoRemocao();
        root = removerPedidoRec(root, id, resultado);
        return resultado.isRemovido();
    }

    // percorre a arvore para remover o pedido desejado

    private Node removerPedidoRec(Node root, int id, ResultadoRemocao resultado) {
        if (root == null) {
            resultado.setRemovido(false);
            return null;
        }

        if (id < root.pedido.getId()) {
            root.left = removerPedidoRec(root.left, id, resultado);
        } else if (id > root.pedido.getId()) {
            root.right = removerPedidoRec(root.right, id, resultado);
        } else {
            resultado.setRemovido(true);

            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            Node sucessor = encontrarSucessor(root.right);
            root.pedido = sucessor.pedido;
            root.right = removerPedidoRec(root.right, sucessor.pedido.getId(), resultado);
        }

        return root;
    }

    //encontra o no sucessor
    private Node encontrarSucessor(Node node) {
        Node atual = node;
        while (atual.left != null) {
            atual = atual.left;
        }
        return atual;
    }
}