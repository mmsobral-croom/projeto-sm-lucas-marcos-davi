import esd.ListaSequencial;
import sm.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("""
                Sistema de comparação de compras: 
                Escreva "Entrar" para começar: 
                """);

        String dadosLista = sc.nextLine();

        // cria acessadores para cada mercado.
        Giassi dadosGiassi = new Giassi();
        Fort dadosFort = new Fort();
        Bistek dadosBistek = new Bistek();

        // Lista com os produtos adicionados.
        ListaSequencial<String> listaCompras = new ListaSequencial<>();

        // Lista dos produtos com o menor preço por mercado, efetivamente o carrinho.
        ListaSequencial<Float> carrinhoGiassi = new ListaSequencial<>();
        ListaSequencial<Float> carrinhoFort = new ListaSequencial<>();
        ListaSequencial<Float> carrinhoBistek = new ListaSequencial<>();

        // Aguarda digitar "entrar"
        while (!dadosLista.equalsIgnoreCase("entrar")) {
            System.out.println("Digite um dado válido. ('entrar' para começar)");
            dadosLista = sc.nextLine();
        }

        // Loop de entrada de produtos
        while (true) {
            System.out.println("Digite um produto que você quer consultar o preço ou finalizar para acabar a compra: ");
            dadosLista = sc.nextLine();

            if (dadosLista.equalsIgnoreCase("finalizar")) {
                break;
            }

            System.out.println(dadosLista);
            listaCompras.adiciona(dadosLista);
        }

        // loop que busca e imprime os itens buscados no API.
        for (int i = 0; i < listaCompras.comprimento(); i++) {
            // Busca em cada mercado
            Float precoGiassi = obtemMenorPreco(dadosGiassi, listaCompras.obtem(i));
            Float precoFort = obtemMenorPreco(dadosFort, listaCompras.obtem(i));
            Float precoBistek = obtemMenorPreco(dadosBistek, listaCompras.obtem(i));

            // Adiciona aos carrinhos
            if (precoGiassi != null) {
                carrinhoGiassi.adiciona(precoGiassi);
                System.out.println("Giassi - " + listaCompras.obtem(i) + ": R$ " + precoGiassi);
            }

            if (precoFort != null) {
                carrinhoFort.adiciona(precoFort);
                System.out.println("Fort - " + listaCompras.obtem(i) + ": R$ " + precoFort);
            }

            if (precoBistek != null) {
                carrinhoBistek.adiciona(precoBistek);
                System.out.println("Bistek - " + listaCompras.obtem(i) + ": R$ " + precoBistek);
            }
        }

        // Exibe carrinhos
        System.out.println("\nCARRINHOS\n");

        Float totalGiassi = exibeCarrinho("Giassi", carrinhoGiassi);
        Float totalFort = exibeCarrinho("Fort", carrinhoFort);
        Float totalBistek = exibeCarrinho("Bistek", carrinhoBistek);

        // Compara os carrinhos
        comparaCarrinhos(totalGiassi, totalFort, totalBistek);
    }

    // Método para obter o menor preço de um produto em um mercado
    static Float obtemMenorPreco(Supermercado mercado, String produtoBuscado) {
        var resultado = mercado.busca(produtoBuscado);

        Float menorPreco = null;

        for (Produto prod : resultado) {
            String nomeProduto = prod.getNome().toLowerCase();
            String busca = produtoBuscado.toLowerCase();
            Float preco = prod.getPreco();

            if (nomeProduto.startsWith(busca) && preco > 0) {
                if (menorPreco == null || preco < menorPreco) {
                    menorPreco = preco;
                }
            }
        }

        return menorPreco;
    }

    // Método para exibir um carrinho
    static Float exibeCarrinho(String nomeMercado, ListaSequencial<Float> carrinho) {
        System.out.println("Carrinho do " + nomeMercado + ":");

        Float total = 0.0f;

        for (int i = 0; i < carrinho.comprimento(); i++) {
            Float preco = carrinho.obtem(i);
            System.out.println("  Produto " + (i + 1) + ": R$ " + preco);
            total += preco;
        }

        System.out.println("  Total: R$ " + total);
        System.out.println();

        return total;
    }

    static void comparaCarrinhos(Float totalGiassi, Float totalFort, Float totalBistek) {
        System.out.println("COMPARAÇÃO FINAL\n");

        System.out.println("Total Giassi: R$ " + totalGiassi);
        System.out.println("Total Fort: R$ " + totalFort);
        System.out.println("Total Bistek: R$ " + totalBistek);

        System.out.println();

        // Verifica qual é o menor
        if (totalGiassi < totalFort && totalGiassi < totalBistek) {
            System.out.println("Giassi é o mais barato!");
        } else if (totalFort < totalGiassi && totalFort < totalBistek) {
            System.out.println("Fort é o mais barato!");
        } else if (totalBistek < totalGiassi && totalBistek < totalFort) {
            System.out.println("Bistek é o mais barato!");
        } else {
            System.out.println("Os preços são iguais!");
        }
    }

}