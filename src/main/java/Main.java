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
        ListaSequencial lista_produtos = new ListaSequencial<>();

        // implementar a busca de multiplos produtos e organização do mesmo


    }
    
    public static void buscaFiltro(String[] args) {

        String searchTerm = args.length > 0 ? String.join(" ", args).trim() : "arroz";
        System.out.println("Buscando produtos: " + searchTerm);

        // cria um acessador para o Giassi
        Giassi sm = new Giassi();
        Bistek sm2 = new Bistek();
        Fort sm3 = new Fort();

        // procura todos produtos cujo nome contenha o termo informado
        ListaSequencial<Produto> produtos_g = sm.busca(searchTerm);
        ListaSequencial<Produto> produtos_b = sm2.busca(searchTerm);
        ListaSequencial<Produto> produtos_f = sm3.busca(searchTerm);

        ListaSequencial<ProductPrice> PriceList_g = new ListaSequencial<>();
        for (int pos=0; pos < produtos_g.comprimento(); pos++) {
            ProductPrice priceOfProduct = new ProductPrice(produtos_g.obtem(pos).getNome(), produtos_g.obtem(pos).getPreco());
            PriceList_g.insere(pos,priceOfProduct);
        }

        ListaSequencial<ProductPrice> PriceList_b = new ListaSequencial<>();
        for (int pos=0; pos < produtos_b.comprimento(); pos++) {
            ProductPrice priceOfProduct = new ProductPrice(produtos_b.obtem(pos).getNome(), produtos_b.obtem(pos).getPreco());
            PriceList_b.insere(pos,priceOfProduct);
        }

        ListaSequencial<ProductPrice> PriceList_f = new ListaSequencial<>();
        for (int pos=0; pos < produtos_f.comprimento(); pos++) {
            ProductPrice priceOfProduct = new ProductPrice(produtos_f.obtem(pos).getNome(), produtos_f.obtem(pos).getPreco());
            PriceList_f.insere(pos,priceOfProduct);
        }

        ListaSequencial<Offer> allOffers = new ListaSequencial<>();
        addOffers(allOffers, PriceList_g, "Giassi");
        addOffers(allOffers, PriceList_b, "Bistek");
        addOffers(allOffers, PriceList_f, "Fort");

        // Agora, ordenar todas as ofertas por preço/kg e mostrar as 2 melhores
        sortOffers(allOffers);
        if (allOffers.esta_vazia()) {
            System.out.println("Nenhuma oferta com preço e tamanho foi encontrada para: " + searchTerm);
            System.out.println("Resultados recebidos das APIs: Giassi=" + produtos_g.comprimento()
                    + ", Bistek=" + produtos_b.comprimento()
                    + ", Fort=" + produtos_f.comprimento());
        } else {
            System.out.println("Melhores opções considerando preço por kg:");
        }
        for (int k = 0; k < Math.min(5, allOffers.comprimento()); k++) {
            Offer o = allOffers.obtem(k);
            System.out.println("Nome: " + o.getName()
                    + ", Tamanho: " + o.getSize()
                    + ", Valor: R$ " + String.format("%.2f", o.getPrice())
                    + ", Mercado: " + o.getMarket()
                    + ", Preço/kg: R$ " + String.format("%.2f", o.getPricePerKg()));
        }

        System.out.println("Fim da busca.");

    }

    private static void addOffers(ListaSequencial<Offer> offers, ListaSequencial<ProductPrice> prices, String market) {
        for (int pos = 0; pos < prices.comprimento(); pos++) {
            ProductPrice p = prices.obtem(pos);
            float size = parseSize(p.getName());
            if (size > 0 && p.getPrice() > 0) {
                float pricePerKg = p.getPrice() / (size / 1000);
                offers.adiciona(new Offer(p.getName(), String.format("%.0fg", size), p.getPrice(), market, pricePerKg));
            }
        }
    }

    private static void sortOffers(ListaSequencial<Offer> offers) {
        int n = offers.comprimento();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (offers.obtem(j).getPricePerKg() > offers.obtem(j + 1).getPricePerKg()) {
                    // swap
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
}

