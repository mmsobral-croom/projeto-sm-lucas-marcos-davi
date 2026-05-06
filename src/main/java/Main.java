import esd.ListaSequencial;
import sm.Giassi;
import sm.Bistek;
import sm.Fort;
import sm.Supermercado;
import sm.Produto;
import model.Offer;

import java.util.Locale;
import java.util.regex.Matcher; // para limpar as saídas
import java.util.regex.Pattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {

    private static final Pattern PESO_PATTERN =
            Pattern.compile("(\\d+(?:[,.]\\d+)?)\\s*(kg|g)", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Informe o nome do arquivo.");
            return;
        }

        String nomeArquivo = args[0];

        try {
            Scanner leitor = new Scanner(new File(nomeArquivo));

            while (leitor.hasNextLine()) {
                String produto = leitor.nextLine().trim();

                if (!produto.isEmpty()) {
                    buscarProduto(produto);
                }
            }

            leitor.close();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
        }
    }


    private static float pegarPesoEmGramas(String nome) {
        Matcher matcher = PESO_PATTERN.matcher(nome);

        if (!matcher.find()) {
            return 0;
        }

        float valor = Float.parseFloat(matcher.group(1).replace(",", "."));
        String unidade = matcher.group(2).toLowerCase(Locale.ROOT);

        if (unidade.equals("kg")) {
            return valor * 1000;
        }

        return valor;
    }

    public static void buscarProduto(String nomeProduto) {
        System.out.println("Buscando produto: " + nomeProduto);

        ListaSequencial<Offer> ofertas = buscarNosMercados(nomeProduto);

        if (ofertas.esta_vazia()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        ordenarPorPrecoKg(ofertas);
        mostrarOfertas(ofertas, 3);
    }

    public static void buscarVariosProdutos(String[] produtos) {
        ListaSequencial<Offer> melhoresOfertas = new ListaSequencial<>();

        for (String produto : produtos) {
            System.out.println("Buscando produto: " + produto);

            Offer melhorOferta = obterMelhorPreco(produto);

            if (melhorOferta != null) {
                melhoresOfertas.adiciona(melhorOferta);
            }
        }

        if (melhoresOfertas.esta_vazia()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        mostrarOfertas(melhoresOfertas, melhoresOfertas.comprimento());
    }


    private static ListaSequencial<Offer> buscarNosMercados(String termoBusca) {
        ListaSequencial<Offer> ofertas = new ListaSequencial<>();

        Giassi giassi = new Giassi();
        Bistek bistek = new Bistek();
        Fort fort = new Fort();

        adicionarOfertas(ofertas, giassi.busca(termoBusca), "Giassi");
        adicionarOfertas(ofertas, bistek.busca(termoBusca), "Bistek");
        adicionarOfertas(ofertas, fort.busca(termoBusca), "Fort");

        return ofertas;
    }

    private static void adicionarOfertas(ListaSequencial<Offer> ofertas,
                                         Supermercado.Resultado resultado,
                                         String mercado) {
        if (resultado == null) {
            return;
        }

        for (Produto produto : resultado) {
            float peso = pegarPesoEmGramas(produto.getNome());

            if (peso > 0 && produto.getPreco() > 0) {
                float precoPorKg = produto.getPreco() / (peso / 1000);

                Offer oferta = new Offer(
                        produto.getNome(),
                        String.format("%.0fg", peso),
                        produto.getPreco(),
                        mercado,
                        precoPorKg
                );

                ofertas.adiciona(oferta);
            }
        }
    }

    public static Offer obterMelhorPreco(String nomeProduto) {
        ListaSequencial<Offer> ofertas = buscarNosMercados(nomeProduto);

        if (ofertas.esta_vazia()) {
            return null;
        }

        ordenarPorPrecoKg(ofertas);
        return ofertas.obtem(0);
    }

    public static ListaSequencial<Offer> obterMelhoresPrecos(String[] produtos) {
        ListaSequencial<Offer> melhoresOfertas = new ListaSequencial<>();

        for (String produto : produtos) {
            Offer melhorOferta = obterMelhorPreco(produto);

            if (melhorOferta != null) {
                melhoresOfertas.adiciona(melhorOferta);
            }
        }

        return melhoresOfertas;
    }

    private static void ordenarPorPrecoKg(ListaSequencial<Offer> ofertas) {
        int tamanho = ofertas.comprimento();

        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho - 1 - i; j++) {
                Offer atual = ofertas.obtem(j);
                Offer proximo = ofertas.obtem(j + 1);

                if (atual.getPricePerKg() > proximo.getPricePerKg()) {
                    ofertas.remove(j + 1);
                    ofertas.remove(j);

                    ofertas.insere(j, proximo);
                    ofertas.insere(j + 1, atual);
                }
            }
        }
    }

    private static void mostrarOfertas(ListaSequencial<Offer> ofertas, int quantidade) {
        int limite = Math.min(quantidade, ofertas.comprimento());

        System.out.println();
        System.out.println("TOP " + limite + " MELHORES PREÇOS");

        for (int i = 0; i < limite; i++) {
            Offer oferta = ofertas.obtem(i);

            System.out.println("------------------------------");
            System.out.println((i + 1) + "º produto");
            System.out.println("Nome: " + oferta.getName());
            System.out.println("Tamanho: " + oferta.getSize());
            System.out.println("Preço: R$ " + String.format("%.2f", oferta.getPrice()));
            System.out.println("Mercado: " + oferta.getMarket());
            System.out.println("Preço por kg: R$ " + String.format("%.2f", oferta.getPricePerKg()));
        }
    }
}
