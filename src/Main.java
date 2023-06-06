import java.util.Date;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        PedidoTree pedidoTree = new PedidoTree();
        Scanner scanner = new Scanner(System.in);

        boolean executar = true;

        while (executar) {
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Adicionar nova venda");
            System.out.println("2 - Visualizar todas as vendas");
            System.out.println("3 - Ver status da venda");
            System.out.println("4 - Atualizar status da venda");
            System.out.println("5 - Exibir ordenação dos pedidos pela data mostrando onde ele se encontra na arvore");
            System.out.println("6 - Remover venda");
            System.out.println("7 - Exibir ordenação dos pedidos por valor");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o ID da venda:");
                    int id = scanner.nextInt();

                    System.out.println("Digite o dia da venda:");
                    int dia = scanner.nextInt();

                    System.out.println("Digite o mês da venda:");
                    int mes = scanner.nextInt();

                    System.out.println("Digite o ano da venda:");
                    int ano = scanner.nextInt();

                    System.out.println("Digite o valor da venda:");
                    double valor = scanner.nextDouble();

                    System.out.println("Digite o status da venda:");
                    scanner.nextLine(); // limpar o buffer
                    String status = scanner.nextLine();

                    // Criar instância de Date com o dia, mês e ano informados
                    Date data = new Date(ano - 1900, mes - 1, dia);

                    Pedido novoPedido = new Pedido(id, data, valor, status);
                    pedidoTree.adicionarPedido(novoPedido);

                    System.out.println("Venda adicionada com sucesso!");

                    System.out.println("Gostaria de continuar o programa? (1 - Sim / 2 - Não)");
                    int resposta = scanner.nextInt();
                    if (resposta != 1) {
                        executar = false;
                    }
                    break;

                case 2:
                    System.out.println("Todas as vendas:");
                    pedidoTree.visualizarTodasAsVendas();
                    break;

                case 3:
                    System.out.println("Digite o ID da venda:");
                    int idVendaStatus = scanner.nextInt();
                    Node pedidoStatus = pedidoTree.buscarPedido(pedidoTree.getRoot(), idVendaStatus);

                    if (pedidoStatus != null) {
                        System.out.println("Status da venda #" + idVendaStatus + ": " + pedidoStatus.pedido.getStatus());
                    } else {
                        System.out.println("Venda não encontrada!");
                    }
                    break;

                case 4:
                    System.out.println("Digite o ID da venda:");
                    int idVendaAtualizar = scanner.nextInt();
                    Node pedidoAtualizarNode = pedidoTree.buscarPedido(pedidoTree.getRoot(), idVendaAtualizar);

                    if (pedidoAtualizarNode != null) {
                        System.out.println("Digite o novo status da venda:");
                        scanner.nextLine(); // Consumir a quebra de linha
                        String novoStatus = scanner.nextLine();

                        pedidoAtualizarNode.pedido.setStatus(novoStatus);

                        System.out.println("Status da venda #" + idVendaAtualizar + " atualizado com sucesso!");
                    } else {
                        System.out.println("Venda não encontrada!");
                    }
                    break;

                case 5:
                    System.out.println("Ordenação dos pedidos pela data mostrando onde ele se encontra na arvore:");
                    pedidoTree.inOrderTraversalWithLocation();
                    break;
/**
 * a venda esta por algum motivo retoranndo venda nao encontrada apos deletar
 */
                case 6:
                    System.out.println("Digite o ID da venda que deseja remover:");
                    int idVendaRemover = scanner.nextInt();
                    boolean removido = pedidoTree.removerPedido(idVendaRemover);

                    if (removido) {
                        System.out.println("Venda #" + idVendaRemover + " removida com sucesso!");
                    } else {
                        System.out.println("Venda não encontrada!");
                    }
                    break;

                    case 7:
                    System.out.println("Ordenação dos pedidos por valor (maior para menor):");
                    pedidoTree.visualizarVendasPorValor();
                    break;

                case 0:
                    executar = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

        System.out.println("Encerrando o programa...");
    }
}
