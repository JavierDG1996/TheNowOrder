package infilms.asee.giiis.unex.es.thenoworder.API;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.Random;

import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

public class NetworkingAndroidHttpClientJSON {

    private List<Product> product_list;

    public NetworkingAndroidHttpClientJSON() {
        new HttpGetTask().execute();
    }

    public List<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<Product> product_list) {
        this.product_list= new ArrayList<>();
        this.product_list=product_list;
    }

    class HttpGetTask extends AsyncTask<Void,Void,List<Product>> {
        private static final String BASE_URL    ="api.spoonacular.com";
        private static final String JSON_SEG    ="recipes";
        private static final String JSON_SEG2   ="random";
        private static final String Number      ="number";
        private static final String apiKey      ="apiKey";
        private static final String apiKeyValue ="f3bba258c5414ebc9083b7241491d522";

        @Override
        protected List<Product> doInBackground(Void... params) {
            URL queryURL;
            JSONObject result= new JSONObject();

            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{JSON_SEG,JSON_SEG2},
                    new Pair(Number, "5"),
                    new Pair(apiKey,apiKeyValue));
            result = NetworkUtils.getJSONResponse(queryURL);

            if (result != null ) {
                Log.v("Lista de Recetas", "Getting response from the API");
                return jsonToList(result);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> items) {
            setProduct_list(items);
            for(Product e : items){
                Log.v("Lista de productos", "API: " + e.getProduct_name());
            }
        }

        public List<Product> jsonToList(JSONObject responseObject) {
            List<Product> Product_list = new ArrayList<>();
            Random r = new Random();
            int price;
            String nombreProduct;
            try {

                if (responseObject != null) {
                    JSONArray Products = responseObject.getJSONArray("recipes");
                    for (int idx = 0; idx < Products.length(); idx++) {
                        // Get single Product data - a Map
                        JSONObject Product = (JSONObject) Products.get(idx);
                        // Delete HTML tags from description
                        //String description = Jsoup.parse(Product.get(DESCRIPTION_TAG).toString()).text();
                        // Build Product object
                        price = r.nextInt(30) ;
                        nombreProduct=Product.get("title").toString();
                        Product ProductObj = new Product(nombreProduct,(float) price);

                        Product_list.add(ProductObj);
                        Log.v("Nombre producto= ",nombreProduct);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            setProduct_list(Product_list);
            return Product_list;
        }
    }
}
