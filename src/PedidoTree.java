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
                    // Se a data do pedido é anterior à data do nó atual, vai para a esquerda
                    current = current.left;

                    if (current == null) {
                        // Se o nó à esquerda é nulo, insere o novo nó ali
                        parent.left = newNode;
                        return;
                    }
                } else {
                    // Se a data do pedido é posterior ou igual à data do nó atual, vai para a direita
                    current = current.right;

                    if (current == null) {
                        // Se o nó à direita é nulo, insere o novo nó ali
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    // Busca um pedido na árvore pelo ID
    public Node buscarPedido(Node root, int id) {
        if (root == null || root.pedido.getId() == id) {
            // Se o nó atual é nulo ou possui o ID buscado, retorna o nó
            return root;
        }

        if (id < root.pedido.getId()) {
            // Se o ID buscado é menor, busca na subárvore esquerda
            return buscarPedido(root.left, id);
        } else {
            // Se o ID buscado é maior, busca na subárvore direita
            return buscarPedido(root.right, id);
        }
    }

    // Visualiza todas as vendas em ordem crescente
    public void visualizarTodasAsVendas() {
        inOrderTraversal(root);
    }

    // Traversa a árvore em ordem crescente e imprime as informações dos pedidos
    private void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.println(root.pedido.toString());
            inOrderTraversal(root.right);
        }
    }

    // Visualiza as vendas ordenadas por valor em ordem decrescente
    public void visualizarVendasPorValor() {
        List<Pedido> pedidos = new ArrayList<>();
        inOrderTraversalToList(root, pedidos);

        pedidos.sort(Comparator.comparingDouble(Pedido::getValor).reversed());

        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toString());
        }
    }

    // Traversa a árvore em ordem crescente e adiciona os pedidos em uma lista
    private void inOrderTraversalToList(Node root, List<Pedido> pedidos) {
        if (root != null) {
            inOrderTraversalToList(root.left, pedidos);
            pedidos.add(root.pedido);
            inOrderTraversalToList(root.right, pedidos);
        }
    }

    // Remove um pedido da árvore pelo ID
    public boolean removerPedido(int id) {
        root = removerPedidoRec(root, id);
        return root != null;
    }

    // Remove recursivamente um pedido da árvore
    private Node removerPedidoRec(Node root, int id) {
        if (root == null) {
            // Se o nó é nulo, retorna nulo
            return null;
        }

        if (id < root.pedido.getId()) {
            // Se o ID buscado é menor, busca na subárvore esquerda
            root.left = removerPedidoRec(root.left, id);
        } else if (id > root.pedido.getId()) {
            // Se o ID buscado é maior, busca na subárvore direita
            root.right = removerPedidoRec(root.right, id);
        } else {
            // Se encontrou o nó com o ID buscado
            if (root.left == null) {
                // Se o nó não tem filho à esquerda, retorna o filho à direita
                return root.right;
            } else if (root.right == null) {
                // Se o nó não tem filho à direita, retorna o filho à esquerda
                return root.left;
            }

            // Se o nó tem dois filhos, encontra o sucessor para substituir o nó
            Node sucessor = encontrarSucessor(root.right);
            root.pedido = sucessor.pedido;
            root.right = removerPedidoRec(root.right, sucessor.pedido.getId());
        }

        return root;
    }

    // Encontra o sucessor de um nó (nó mais à esquerda na subárvore direita)
    private Node encontrarSucessor(Node node) {
        Node atual = node;
        while (atual.left != null) {
            atual = atual.left;
        }
        return atual;
    }
}
