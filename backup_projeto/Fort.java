package sm;

import esd.ListaSequencial;

import java.net.URISyntaxException;

public class Fort extends Supermercado {

    public Fort() {
        super("https://www.deliveryfort.com.br");
    }

    public ListaSequencial<Produto> busca(String produto) {
        try {
            return super.busca(produto);
        } catch (URISyntaxException e) {
            return new ListaSequencial<>();
        }
    }

    public Produto obtem(String produto_id) {
        try {
            return super.obtem(produto_id);
        } catch (URISyntaxException e) {
            return null;
        }
    }

}
