package sm;

import esd.ListaSequencial;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Supermercado {

    HttpClient cliente;
    String url;
    final Pattern re_resources = Pattern.compile("(\\d+)-(\\d+)/(\\d+)", Pattern.CASE_INSENSITIVE);
    final int query_len = 40;

    public class Resultado implements Iterable<Produto> {
        String produto;
        int total;
        ListaSequencial<Produto> produtos;
        Supermercado sm;

        Resultado(Supermercado sm, String produto, ListaSequencial<Produto> produtos, int total) {
            this.produto = produto;
            this.produtos = produtos;
            this.total = total;
            this.sm = sm;
        }

        @Override
        public Iterador iterator() {
            return new Iterador(produtos, total);
        }

        public Stream<Produto> stream() {
            return StreamSupport.stream(new ResultIterator(this), false);
        }

        class ResultIterator implements Spliterator<Produto> {
            Iterador it;

            ResultIterator(Resultado res) {
                this.it = res.iterator();
            }

            public void forEachRemaining(Consumer<? super Produto> action) {
                while (it.hasNext()) {
                    action.accept(it.next());
                }
            }

            public boolean tryAdvance(Consumer<? super Produto> action) {
                if (it.hasNext()) {
                    action.accept(it.next());
                    return true;
                }
                else // cannot advance
                    return false;
            }

            public Spliterator<Produto> trySplit() {
                return null;
            }

            public long estimateSize() {
                return (long)(it.total - it.inicio);
            }

            public int characteristics() {
                return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
            }
        }

        class Iterador implements Iterator<Produto> {
            int total;
            int inicio = 0;
            //            int pos = 0;
            ListaSequencial<Produto> produtos;

            Iterador(ListaSequencial<Produto> produtos, int total) {
                this.produtos = produtos;
                this.total = total;
            }

            @Override
            public boolean hasNext() {
                return total > inicio;
            }

            @Override
            public Produto next() {
                if (! hasNext()) {
                    throw new NoSuchElementException("fim da iteração");
                }
                Produto prod = produtos.obtem(inicio++);
                if (inicio >= produtos.comprimento()) {
//                    inicio = produtos.comprimento() + inicio;
                    if (inicio < total) {
                        var mais_produtos = sm.busca_proximo(produto, inicio);
                        if (produtos != null) {
                            for (int j=0; j < mais_produtos.comprimento(); j++) {
                                produtos.adiciona(mais_produtos.obtem(j));
                            }
//                            pos = 0;
                        }
                    }
                }
                return prod;

            }
        }
    }

    public Supermercado(String url)  {
        cliente = HttpClient.newHttpClient();
        this.url = url+ "/api/catalog_system/pub/products/search/";
    }

    String make_url(String produto, int inicio) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.url);
        sb.append("?ft=");
        sb.append(URLEncoder.encode(produto, StandardCharsets.UTF_8));
        sb.append("&_from=");
        sb.append(Integer.toString(inicio));
        sb.append("&_to=");
        sb.append(Integer.toString(inicio+query_len-1));

        return sb.toString();
    }

    String make_get_url(String produtoId) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.url);
        sb.append("?fq=productId:");
        sb.append(produtoId);

        return sb.toString();
    }


    HttpResponse<String> envia(String url) {
        HttpResponse<String> response = null;

        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .setHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64; rv:140.0) Gecko/20100101 Firefox/140.0")
                    .build();


            try {
                response = cliente.send(req, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {

            }
        } catch (URISyntaxException e) {

        }

        return response;
    }

    ListaSequencial<Produto> extrai_produtos(HttpResponse<String> response) {
        ListaSequencial<Produto> r = new ListaSequencial<>();
        var headers = response.headers().map();
        boolean isJson = headers.get("content-type").stream().anyMatch(x -> x.startsWith("application/json"));
        if (isJson) {
            JSONArray jo = new JSONArray(response.body());
            for (var o : jo) {
                JSONObject obj = (JSONObject) o;
                r.adiciona(Produto.fromJson(obj));
            }
        }
        return r;
    }

    ListaSequencial<Produto> busca_proximo(String produto, int inicio) {
        HttpResponse<String> response = envia(make_url(produto, inicio));
        if (response != null) {
            int status = response.statusCode();
            if (status == 200 || status == 206) {
                return extrai_produtos(response);
            }
        }

        return null;
    }

    int[] obtem_info_paginas(HttpResponse<String> response) {
        var headers = response.headers().map();

        String faixa = headers.get("resources").getFirst();
        var m = re_resources.matcher(faixa);
        int paginas[] = {0, 9, 10};
        if (m.find()) {
            paginas[0] = Integer.parseInt(m.group(1));
            paginas[1] = Integer.parseInt(m.group(2));
            paginas[2] = Integer.parseInt(m.group(3));
            paginas[1] = Math.min(paginas[1], paginas[2]);
        }
        return paginas;
    }

    public Resultado busca(String produto) {
        Resultado res = null;

        HttpResponse<String> response = envia(make_url(produto, 0));
        if (response != null) {
            int status = response.statusCode();
            if (status == 200 || status == 206) {
                ListaSequencial<Produto> r = extrai_produtos(response);

                int[] faixa = obtem_info_paginas(response);

                res = new Resultado(this, produto, r, faixa[2]);
            }
        }
        return res;
    }


    public Produto obtem(String produto_id) {
        Produto prod = null;

        HttpResponse<String> response = envia(make_get_url(produto_id));
        if (response != null) {
            int status = response.statusCode();
            if (status == 200) {
                var headers = response.headers().map();
                boolean isJson = headers.get("content-type").stream().anyMatch(x -> x.startsWith("application/json"));
                if (isJson) {
                    JSONArray jo = new JSONArray(response.body());
                    prod = Produto.fromJson(jo.getJSONObject(0));
                }
            }
        }

        return prod;
    }
}