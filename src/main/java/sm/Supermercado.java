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
import java.util.List;

public class Supermercado {

    HttpClient cliente;
    String url;

    public Supermercado(String url)  {
        cliente = HttpClient.newHttpClient();
        this.url = url+ "/api/catalog_system/pub/products/search/";
    }

    public ListaSequencial<Produto> busca(String produto) throws URISyntaxException {
        String url = this.url + "?ft=" + URLEncoder.encode(produto, StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .setHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64; rv:140.0) Gecko/20100101 Firefox/140.0")
                .build();

        ListaSequencial<Produto> r = new ListaSequencial<>();
        try {
            HttpResponse<String> response = cliente.send(req, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                var headers = response.headers().map();
                boolean isJson = headers.getOrDefault("content-type", List.of()).stream().anyMatch(x -> x.startsWith("application/json"));
                if (isJson) {
                    JSONArray jo = new JSONArray(response.body());
                    for (var o: jo) {
                        JSONObject obj = (JSONObject)o;
                        r.adiciona(Produto.fromJson(obj));
                    }
                }
            }
        } catch (IOException | InterruptedException e ) {

        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }

        return r;
    }

    public Produto obtem(String produto_id) throws URISyntaxException {
        String url = this.url + "?fq=productId:" + URLEncoder.encode(produto_id, StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .setHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64; rv:140.0) Gecko/20100101 Firefox/140.0")
                .build();

        Produto prod = null;
        try {
            HttpResponse<String> response = cliente.send(req, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                var headers = response.headers().map();
                boolean isJson = headers.getOrDefault("content-type", List.of()).stream().anyMatch(x -> x.startsWith("application/json"));
                if (isJson) {
                    JSONArray jo = new JSONArray(response.body());
                    prod = Produto.fromJson(jo.getJSONObject(0));
                }
            }
        } catch (IOException | InterruptedException e ) {

        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }

        return prod;
    }
}
