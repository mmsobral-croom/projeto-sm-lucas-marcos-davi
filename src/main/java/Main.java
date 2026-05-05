import esd.ListaSequencial;
import sm.Giassi;
import sm.Bistek;
import sm.Fort;

import sm.Produto;

import model.ProductPrice;
import model.BestPrice;
import model.Offer;

public class Main {

    private static float parseSize(String name) {
        // Procurar por tamanho em g ou kg
        String[] parts = name.split(" ");
        for (String part : parts) {
            if (part.endsWith("kg")) {
                String numStr = part.substring(0, part.length() - 2);
                try {
                    return Float.parseFloat(numStr) * 1000; // converter para g
                } catch (NumberFormatException e) {}
            } else if (part.endsWith("g") && !part.equals("g")) {
                String numStr = part.substring(0, part.length() - 1);
                try {
                    return Float.parseFloat(numStr);
                } catch (NumberFormatException e) {}
            }
        }
        return 0; // se não encontrar, assumir 0
    }
    
    public static void main(String[] args) {

        System.out.println("Buscando produtos...");

        // cria um acessador para o Giassi
        Giassi sm = new Giassi();
        Bistek sm2 = new Bistek();
        Fort sm3 = new Fort();

        String searchTerm = "banana";


        // procura todos produtos cujo nome contenha "tapioca"
        ListaSequencial<Produto> produtos_g = sm.busca(searchTerm);
        ListaSequencial<Produto> produtos_b = sm2.busca(searchTerm);
        ListaSequencial<Produto> produtos_f = sm3.busca(searchTerm);


        // for (int pos=0; pos < produtos_b.comprimento(); pos++) {
        //     IO.println(produtos_b.obtem(pos));
        // }

        // for (int pos=0; pos < produtos_f.comprimento(); pos++) {
        //     IO.println(produtos_f.obtem(pos));
        // }

        // // Mostra cada um dos produtos encontrados
        // for (int pos=0; pos < produtos_g.comprimento(); pos++) {
        //     IO.println(produtos_g.obtem(pos));
        // }

        ListaSequencial<ProductPrice> PriceList_g = new ListaSequencial<>();
        for (int pos=0; pos < produtos_g.comprimento(); pos++) {
            ProductPrice priceOfProduct = new ProductPrice(produtos_g.obtem(pos).getNome(), produtos_g.obtem(pos).getPreco());
            PriceList_g.insere(pos,priceOfProduct);
        }
    
        // for (int pos=0; pos < PriceList_g.comprimento(); pos++) {
        //     IO.println(PriceList_g.obtem(pos).getName() + ": " + PriceList_g.obtem(pos).getPrice());
        // }

        ListaSequencial<ProductPrice> PriceList_b = new ListaSequencial<>();
        for (int pos=0; pos < produtos_b.comprimento(); pos++) {
            ProductPrice priceOfProduct = new ProductPrice(produtos_b.obtem(pos).getNome(), produtos_b.obtem(pos).getPreco());
            PriceList_b.insere(pos,priceOfProduct);
        }
        // for (int pos=0; pos < PriceList_b.comprimento(); pos++) {
        //     IO.println(PriceList_b.obtem(pos).getName() + ": " + PriceList_b.obtem(pos).getPrice());
        // }

        ListaSequencial<ProductPrice> PriceList_f = new ListaSequencial<>();
        for (int pos=0; pos < produtos_f.comprimento(); pos++) {
            ProductPrice priceOfProduct = new ProductPrice(produtos_f.obtem(pos).getNome(), produtos_f.obtem(pos).getPreco());
            PriceList_f.insere(pos,priceOfProduct);
        }
        // for (int pos=0; pos < PriceList_f.comprimento(); pos++) {
        //     IO.println(PriceList_f.obtem(pos).getName() + ": " + PriceList_f.obtem(pos).getPrice());
        // }


        // Coletar todas as ofertas
        ListaSequencial<Offer> allOffers = new ListaSequencial<>();

        // Adicionar do Giassi
        for (int pos = 0; pos < PriceList_g.comprimento(); pos++) {
            ProductPrice p = PriceList_g.obtem(pos);
            float size = parseSize(p.getName());
            if (size > 0 && p.getPrice() > 0) {
                float pricePerKg = p.getPrice() / (size / 1000);
                allOffers.adiciona(new Offer(p.getName(), size + "g", p.getPrice(), "Giassi", pricePerKg));
            }
        }

        // Adicionar do Bistek
        for (int pos = 0; pos < PriceList_b.comprimento(); pos++) {
            ProductPrice p = PriceList_b.obtem(pos);
            float size = parseSize(p.getName());
            if (size > 0 && p.getPrice() > 0) {
                float pricePerKg = p.getPrice() / (size / 1000);
                allOffers.adiciona(new Offer(p.getName(), size + "g", p.getPrice(), "Bistek", pricePerKg));
            }
        }

        // Adicionar do Fort
        for (int pos = 0; pos < PriceList_f.comprimento(); pos++) {
            ProductPrice p = PriceList_f.obtem(pos);
            float size = parseSize(p.getName());
            if (size > 0 && p.getPrice() > 0) {
                float pricePerKg = p.getPrice() / (size / 1000);
                allOffers.adiciona(new Offer(p.getName(), size + "g", p.getPrice(), "Fort", pricePerKg));
            }
        }

        // Agora, ordenar todas as ofertas por preço/kg e mostrar as 2 melhores
        sortOffers(allOffers);
        System.out.println("Duas melhores opções considerando preço por kg:");
        for (int k = 0; k < Math.min(2, allOffers.comprimento()); k++) {
            Offer o = allOffers.obtem(k);
            System.out.println("Nome: " + o.getName() + ", Tamanho: " + o.getSize() + ", Valor: " + o.getPrice() + ", Mercado: " + o.getMarket() + ", Preço/kg: " + String.format("%.2f", o.getPricePerKg()));
        }




        //System.out.println(produtos_g.comprimento() + " produtos encontrados.");
        //ListaSequencial<ProductPrice> PriceList = new ListaSequencial<>();

        //for (int pos=0; pos < produtos_g.comprimento(); pos++) {
        //    ProductPrice priceOfProduct = new ProductPrice(produtos_g.obtem(pos).getNome(), produtos_g.obtem(pos).getPreco());
        //    PriceList.insere(pos,priceOfProduct);
        //}
        // System.out.println(produtos.obtem(0).getPreco());


        //float sum = 0;

        //System.out.println("Lista de preços: ");
        //for (int pos=0; pos < PriceList.comprimento(); pos++) {
        //    IO.println(PriceList.obtem(pos).getName() + ": " + PriceList.obtem(pos).getPrice());
        //   sum += PriceList.obtem(pos).getPrice();
        //}

        //System.out.println("Preço total: " + sum / produtos.comprimento());

        System.out.println("Fim da busca.");

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
