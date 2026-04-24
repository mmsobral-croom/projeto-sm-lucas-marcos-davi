import esd.ListaSequencial;
import sm.Giassi;
import sm.Produto;

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

    }
}
