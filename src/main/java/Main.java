import esd.ListaSequencial;
import sm.Bistek;
import sm.Fort;
import sm.Giassi;
import sm.Produto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("""
                Sistema de comparação de compras: 
                Escreva "Entrar" para começar e "Sair" para sair.
                Digite:
                """);

        String dadosLista = sc.nextLine();

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

        // While feito para que só entre no menu, quando digitar entrar.
        while (!dadosLista.equalsIgnoreCase("entrar")) {
            System.out.println("Digite um dado válido.");
            dadosLista = sc.nextLine();
        }

        if (dadosLista.equalsIgnoreCase("entrar")) {
            // Enquanto não colocar sair, será adicionado produtos para entrar na listaCompras
            while (!dadosLista.equalsIgnoreCase("sair")) {
                System.out.println("Digite um produto que você quer consultar o preço: ");
                dadosLista = sc.nextLine();

                listaCompras.adiciona(dadosLista);
            }

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
            
        }
    }
}