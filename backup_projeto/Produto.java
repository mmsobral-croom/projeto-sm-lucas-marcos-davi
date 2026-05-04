package sm;

import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;

@Builder
@Value
public class Produto {
        String nome;
        String id;
        String marca;
        float preco;
        boolean disponivel;


        static JSONObject getOffer(JSONObject obj) {
                JSONArray items = obj.getJSONArray("items");
                JSONObject item = items.getJSONObject(0);
                JSONArray sellers = item.getJSONArray("sellers");
                JSONObject seller = sellers.getJSONObject(0);
                JSONObject offer = seller.getJSONObject("commertialOffer");
                return offer;
        }

        static Produto.ProdutoBuilder fromJsonBuilder(JSONObject obj) {
                Produto.ProdutoBuilder pb = Produto.builder()
                        .nome((String)obj.get("productName"))
                        .id((String)obj.get("productId"))
                        .marca((String)obj.get("brand"));
                try {
                        JSONObject offer = Produto.getOffer(obj);
                        float preco = offer.getBigDecimal("Price").floatValue();
                        pb.preco(preco);
                        boolean available = offer.getBoolean("IsAvailable");
                        pb.disponivel(available);
                } catch (Exception e) {
//                        IO.println(e);
                }
                return pb;

        }

        static Produto fromJson(JSONObject obj) {
                var pb = Produto.fromJsonBuilder(obj);

                return pb.build();
        }

}
