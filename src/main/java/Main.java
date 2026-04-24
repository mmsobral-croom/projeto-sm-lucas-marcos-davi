import esd.ListaSequencial;
import sm.Giassi;
import sm.Produto;

import model.ProductPrice;

public class Main {

    
    static void main() {

        System.out.println("Buscando produtos...");

        // cria um acessador para o Giassi
        Giassi sm = new Giassi();

        // procura todos produtos cujo nome contenha "tapioca"
        ListaSequencial<Produto> produtos = sm.busca("tapioca");

        // Mostra cada um dos produtos encontrados
        for (int pos=0; pos < produtos.comprimento(); pos++) {
            IO.println(produtos.obtem(pos));
        }

        System.out.println(produtos.comprimento() + " produtos encontrados.");
        ListaSequencial<ProductPrice> PriceList = new ListaSequencial<>();

        for (int pos=0; pos < produtos.comprimento(); pos++) {
            ProductPrice priceOfProduct = new ProductPrice(produtos.obtem(pos).getNome(), produtos.obtem(pos).getPreco());
            PriceList.insere(pos,priceOfProduct);
        }
        // System.out.println(produtos.obtem(0).getPreco());


        float sum = 0;

        System.out.println("Lista de preços: ");
        for (int pos=0; pos < PriceList.comprimento(); pos++) {
            IO.println(PriceList.obtem(pos).getName() + ": " + PriceList.obtem(pos).getPrice());
            sum += PriceList.obtem(pos).getPrice();
        }

        System.out.println("Preço total: " + sum / produtos.comprimento());

    }
}
