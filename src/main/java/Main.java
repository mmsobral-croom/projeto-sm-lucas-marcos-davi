import esd.ListaSequencial;
import sm.Bistek;
import sm.Fort;
import sm.Giassi;
import sm.Produto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // cria acessadores para cada mercado.
        Giassi dadosGiassi = new Giassi();
        Fort dadosFort = new Fort();
        Bistek dadosBistek = new Bistek();

        // Lista com os produtos adicionados.
        ListaSequencial<String> listaCompras = new ListaSequencial<>();

        // Listas dos preços dos produtos por mercado.
        ListaSequencial<Float> listaPrecosGiassi = new ListaSequencial<>();
        ListaSequencial<Float> listaPrecosFort = new ListaSequencial<>();
        ListaSequencial<Float> listaPrecosBistek = new ListaSequencial<>();

        IO.println("Bem vindo à procura de produtos automática");
        IO.println("Digite um produto que voce quer consultar o preco. ");
        IO.println("Caso deseje consultar os produtos ja digitados, digite consultar");
        String dadosLista = sc.nextLine();

        //Enquanto os dados da lista forem diferentes de sair, ele vai continuar adicionando
        while (!dadosLista.equalsIgnoreCase("sair")) {

            //Se for digitado "consultar" nos dados da lista, ele irá retorar a busca pela API
            if (dadosLista.equalsIgnoreCase("consultar")) {

                // loop que busca e imprime os itens buscados no API.
                for (int i = 0; i < listaCompras.comprimento() - 1; i++) {
                    String produtoNome = listaCompras.obtem(i);

                    // Variáveis busca para buscar os nomes dos produtos digitados.
                    var buscaGiassiNome = dadosGiassi.busca(produtoNome);
                    var buscaFortNome = dadosFort.busca(produtoNome);
                    var buscaBistekNome = dadosBistek.busca(produtoNome);

                    // Váriaveis busca para buscar os IDs dos produtos.
                    var buscaGiassiId = dadosGiassi.obtem(produtoNome);
                    var buscaFortId = dadosFort.obtem(produtoNome);
                    var buscaBistekId = dadosBistek.obtem(produtoNome);

                    System.out.println("Preço dos produtos do Giassi:");
                    System.out.println();

                    buscaGiassiNome.stream()
                            .forEach(prod ->
                                    System.out.println(prod.getNome() + " - R$: " + prod.getPreco()));

                    // Adiciona o preço de cada produto na lista respectiva do mercado.
                    buscaGiassiNome.stream()
                            .forEach(prod ->
                                    listaPrecosGiassi.adiciona(prod.getPreco()));

                    System.out.println();
                    System.out.println("Preço dos produtos do Fort:");
                    System.out.println();

                    buscaFortNome.stream()
                            .forEach(prod ->
                                    System.out.println(prod.getNome() + " - R$: " + prod.getPreco()));

                    buscaFortNome.stream()
                            .forEach(prod ->
                                    listaPrecosFort.adiciona(prod.getPreco()));

                    System.out.println();
                    System.out.println("Preço dos produtos do Bistek:");
                    System.out.println();

                    buscaBistekNome.stream()
                            .forEach(prod ->
                                    System.out.println(prod.getNome() + " - R$: " + prod.getPreco()));

                    buscaBistekNome.stream()
                            .forEach(prod ->
                                    listaPrecosBistek.adiciona(prod.getPreco()));

                }
                System.out.println();

                for (int i = 0; i < listaPrecosGiassi.comprimento(); i++) {
                    System.out.println("Preço: R$ " + listaPrecosGiassi.obtem(i));
                }

                System.out.println();

                for (int i = 0; i < listaPrecosFort.comprimento(); i++) {
                    System.out.println("Preço: R$ " + listaPrecosFort.obtem(i));
                }

                System.out.println();

                for (int i = 0; i < listaPrecosBistek.comprimento(); i++) {
                    System.out.println("Preço: R$ " + listaPrecosBistek.obtem(i));
                }

                //Se não for digitado, consultar ele vai adicionar o produto na lista
            } else {
                // ADICIONAR PRODUTO
                listaCompras.adiciona(dadosLista);
                System.out.println("Produto adicionado!");
            }

            // Pergunta se quer outro produto ou não
            System.out.println("\nDigite outro produto, 'consultar' ou 'sair':");
            dadosLista = sc.nextLine();
        }
    }
}