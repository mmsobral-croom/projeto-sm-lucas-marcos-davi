import esd.ListaSequencial;
import sm.Bistek;
import sm.Fort;
import sm.Giassi;
import sm.Produto;

import java.util.Scanner;

public class Main {
    public static void main() {

        Scanner sc = new Scanner(System.in);

        System.out.println("""
                Sistema de comparação de compras: 
                Pressione Entrar para começar e 0 para sair.
                Digite:
                """);


        String dadosLista = sc.nextLine();

        // cria acessadores para cada mercado.
        Giassi dadosGiassi = new Giassi();
        Fort dadosFort = new Fort();
        Bistek dadosBistek = new Bistek();

        // Lista com os produtos adicionados.
        ListaSequencial<String> listaCompras = new ListaSequencial<>();

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
            for (int i = 0; i < listaCompras.comprimento(); i++) {
                String produto = listaCompras.obtem(i);

                // Variáveis busca para buscar dados dos produtos digitados.
                var buscaGiassi = dadosGiassi.busca(produto);
                var buscaFort = dadosFort.busca(produto);
                var buscaBistek = dadosBistek.busca(produto);

                buscaGiassi.stream()
                        .map(prod -> prod.getNome())
                        .filter(nome -> nome.startsWith(produto))
                        .forEach(nome -> IO.println(nome));

                buscaFort.stream()
                        .map(prod -> prod.getNome())
                        .filter(nome -> nome.startsWith(produto))
                        .forEach(nome -> IO.println(nome));

                buscaBistek.stream()
                        .map(prod -> prod.getNome())
                        .filter(nome -> nome.startsWith(produto))
                        .forEach(nome -> IO.println(nome));

            }

        }

        /*
        // procura todos produtos cujo nome contenha "tapioca"
        var buscaGiassi = dadosGiassi.busca("carne");

        // Mostra cada um dos produtos encontrados
        for (Produto prod: buscaGiassi) {
            IO.println(prod);
        }

        // Mostra os nomes dos produtos que iniciam exatamente com "Tapioca", mas usando a API stream:
        buscaGiassi.stream()
                .map(produto -> produto.getNome())
                .filter(nome -> nome.startsWith("Carne"))
                .forEach(nome -> IO.println(nome));

        */
    }
}