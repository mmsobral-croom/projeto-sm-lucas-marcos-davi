import esd.ListaSequencial;
import sm.Giassi;
import sm.Bistek;
import sm.Fort;
import sm.Produto;
import model.ProductPrice;
import model.Offer;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+(?:[,.]\\d+)?)\\s*(kg|g)\\b", Pattern.CASE_INSENSITIVE);

    private static float parseSize(String name) {
        Matcher matcher = SIZE_PATTERN.matcher(name);
        if (!matcher.find()) {
            return 0;
        }

        float amount = Float.parseFloat(matcher.group(1).replace(',', '.'));
        String unit = matcher.group(2).toLowerCase(Locale.ROOT);
        if (unit.equals("kg")) {
            return amount * 1000;
        }

        return amount;
    }

    public static void main(String[] args) {
        // Descomente abaixo para usar os novos métodos
        
        System.out.println(args[0]);
        buscarEFiltrarProduto(args[0]);
        //String[] produtos = {"arroz", "feijão", "açúcar"};
        //buscarEFiltrarMultiplosProdutos(produtos);
    }

    
    //Busca e filtra um único produto em todos os mercados
    public static void buscarEFiltrarProduto(String nomeProduto) {
        System.out.println("Buscando: " + nomeProduto);
        
        ListaSequencial<Offer> todasOfertas = buscarEmTodosMercados(nomeProduto);
        
        if (todasOfertas.esta_vazia()) {
            System.out.println("Nenhum produto encontrado para: " + nomeProduto);
            return;
        }
        
        sortOffers(todasOfertas);
        exibirMelhoresOfertas(todasOfertas, 3);
    }

    // Busca e filtra múltiplos produtos em todos os mercados
    public static void buscarEFiltrarMultiplosProdutos(String[] produtos) {
        ListaSequencial<Offer> todasOfertas = new ListaSequencial<>();
        
        for (String produto : produtos) {
            System.out.println("Buscando: " + produto);
            ListaSequencial<Offer> ofertas = buscarEmTodosMercados(produto);
            
            // Adicionar todas as ofertas encontradas
            for (int i = 0; i < ofertas.comprimento(); i++) {
                todasOfertas.adiciona(ofertas.obtem(i));
            }
        }
        
        if (todasOfertas.esta_vazia()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }
        
        System.out.println("\nOrdenando todas as ofertas por preço/kg...\n");
        sortOffers(todasOfertas);
        exibirMelhoresOfertas(todasOfertas, 5);
    }

    ///Busca um produto em todos os mercados disponíveis

    private static ListaSequencial<Offer> buscarEmTodosMercados(String termoBusca) {
        ListaSequencial<Offer> todasOfertas = new ListaSequencial<>();
        
        // Busca em Giassi
        Giassi sm1 = new Giassi();
        ListaSequencial<Produto> produtos1 = sm1.busca(termoBusca);
        addOffers(todasOfertas, produtos1, "Giassi");
        
        // Busca em Bistek
        Bistek sm2 = new Bistek();
        ListaSequencial<Produto> produtos2 = sm2.busca(termoBusca);
        addOffers(todasOfertas, produtos2, "Bistek");
        
        // Busca em Fort
        Fort sm3 = new Fort();
        ListaSequencial<Produto> produtos3 = sm3.busca(termoBusca);
        addOffers(todasOfertas, produtos3, "Fort");
        
        return todasOfertas;
    }


    //Retorna o melhor preço (menor preço/kg) e o mercado correspondente
 
    public static Offer obterMelhorPreco(String nomeProduto) {
        ListaSequencial<Offer> ofertas = buscarEmTodosMercados(nomeProduto);
        
        if (ofertas.esta_vazia()) {
            return null;
        }
        
        sortOffers(ofertas);
        return ofertas.obtem(0); // Retorna a primeira (melhor preço)
    }

    //Retorna a melhor oferta para cada produto da lista
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

    
    //Adiciona ofertas de um produto a partir de uma lista de produtos
     
    private static void addOffers(ListaSequencial<Offer> offers, ListaSequencial<Produto> produtos, String market) {
        for (int pos = 0; pos < produtos.comprimento(); pos++) {
            Produto p = produtos.obtem(pos);
            float size = parseSize(p.getNome());
            if (size > 0 && p.getPreco() > 0) {
                float pricePerKg = p.getPreco() / (size / 1000);
                offers.adiciona(new Offer(
                    p.getNome(),
                    String.format("%.0fg", size),
                    p.getPreco(),
                    market,
                    pricePerKg
                ));
            }
        }
    }

    
    // Ordena as ofertas por preço/kg (menor para maior)
    private static void sortOffers(ListaSequencial<Offer> offers) {
        int n = offers.comprimento();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (offers.obtem(j).getPricePerKg() > offers.obtem(j + 1).getPricePerKg()) {
                    // Swap
                    Offer temp = offers.obtem(j);
                    Offer next = offers.obtem(j + 1);
                    offers.remove(j + 1);
                    offers.remove(j);
                    offers.insere(j, next);
                    offers.insere(j + 1, temp);
                }
            }
        }
    }

    
    // Exibe as melhores ofertas formatadas
    private static void exibirMelhoresOfertas(ListaSequencial<Offer> offers, int quantidade) {
        int limite = Math.min(quantidade, offers.comprimento());
        
        System.out.println("┌─ TOP " + limite + " MELHORES PREÇOS ─────────────────────────┐");
        for (int i = 0; i < limite; i++) {
            Offer o = offers.obtem(i);
            System.out.println("│ #" + (i + 1));
            System.out.println("│   Produto: " + o.getName());
            System.out.println("│   Tamanho: " + o.getSize());
            System.out.println("│   Valor: R$ " + String.format("%.2f", o.getPrice()));
            System.out.println("│   Mercado: " + o.getMarket());
            System.out.println("│   Preço/kg: R$ " + String.format("%.2f", o.getPricePerKg()));
            System.out.println("│");
        }
        System.out.println("└──────────────────────────────────────────────┘");
    }
}