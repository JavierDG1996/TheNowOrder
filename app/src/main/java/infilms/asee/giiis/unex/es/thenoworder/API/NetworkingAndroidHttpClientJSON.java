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
    private List<Product> listDessert;
    private List<Product> listDrinks;

    public NetworkingAndroidHttpClientJSON() {
        new HttpGetTask().execute();
    }

    public List<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<Product> product_list) {
        this.product_list = new ArrayList<>();
        this.product_list = product_list;
    }

    public List<Product> getListDessert() {
        return listDessert;
    }

    public void setListDessert(List<Product> listDessert) {
        this.listDessert = new ArrayList<>();
        this.listDessert = listDessert;
    }

    public List<Product> getListDrinks() {
        return listDrinks;
    }

    public void setListDrinks(List<Product> listDrinks) {
        this.listDrinks = new ArrayList<>();
        this.listDrinks = listDrinks;
    }

    class HttpGetTask extends AsyncTask<Void, Void, List<Product>> {
        private static final String BASE_URL = "api.spoonacular.com";
        private static final String JSON_SEG = "recipes";
        private static final String JSON_SEG2 = "random";
        private static final String Number = "number";
        private static final String apiKey = "apiKey";
        private static final String apiKeyValue = "f3bba258c5414ebc9083b7241491d522";
        private static final String tipo = "type";
        private static final String postre = "dessert";
        //https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic
        private static final String BASE_URLdrink = "www.thecocktaildb.com";
        private static final String path = "api";
        private static final String path2 = "json";
        private static final String path3 = "v1";
        private static final String path4 = "1";
        private static final String path5 = "filter.php";
        private static final String param = "Non_Alcoholic";

        @Override
        protected List<Product> doInBackground(Void... params) {
            URL queryURL;
            JSONObject[] result = new JSONObject[3];

            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{JSON_SEG, JSON_SEG2},
                    new Pair(Number, "5"),
                    new Pair(apiKey, apiKeyValue));
            result[0] = NetworkUtils.getJSONResponse(queryURL);

            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{JSON_SEG, JSON_SEG2},
                    new Pair(tipo, postre),
                    new Pair(Number, "5"),
                    new Pair(apiKey, apiKeyValue));
            result[1] = NetworkUtils.getJSONResponse(queryURL);

            queryURL = NetworkUtils.buildURL(BASE_URLdrink,
                    new String[]{path, path2, path3, path4, path5},
                    new Pair("a", param));
            result[2] = NetworkUtils.getJSONResponse(queryURL);

            if (result != null) {
                Log.v("Lista de Recetas", "Getting response from the API");
                return jsonToList(result);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> items) {
            setProduct_list(items);
            for (Product e : items) {
                Log.v("Lista de productos", "API: " + e.getProduct_name());
            }
        }

        public List<Product> jsonToList(JSONObject[] responseObject) {
            List<Product> Product_list = new ArrayList<>();
            List<Product> Dessert_list = new ArrayList<>();
            List<Product> Drinks_list = new ArrayList<>();
            Random r = new Random();
            int price;
            String nombreProduct;
            JSONArray Products;
            try {

                if (responseObject[0] != null) {
                    Products = responseObject[0].getJSONArray("recipes");

                    //JSON de comida a Lista de comida
                    for (int idx = 0; idx < Products.length(); idx++) {
                        // Get single Product data - a Map
                        JSONObject Product = (JSONObject) Products.get(idx);
                        // Delete HTML tags from description
                        //String description = Jsoup.parse(Product.get(DESCRIPTION_TAG).toString()).text();
                        // Build Product object
                        price = r.nextInt(30);
                        nombreProduct = Product.get("title").toString();
                        Product ProductObj = new Product(nombreProduct, (float) price);

                        Product_list.add(ProductObj);
                        Log.v("Nombre producto= ", nombreProduct);
                    }
                }
                if (responseObject[1] != null) {
                    Products = responseObject[1].getJSONArray("recipes");
                    for (int idx = 0; idx < Products.length(); idx++) {
                        // Get single Product data - a Map
                        JSONObject Product = (JSONObject) Products.get(idx);
                        // Delete HTML tags from description
                        //String description = Jsoup.parse(Product.get(DESCRIPTION_TAG).toString()).text();
                        // Build Product object
                        price = r.nextInt(30);
                        nombreProduct = Product.get("title").toString();
                        Product ProductObj = new Product(nombreProduct, (float) price);

                        Dessert_list.add(ProductObj);
                        Log.v("Nombre producto= ", nombreProduct);
                    }
                }
                if (responseObject[2] != null) {
                    Products = responseObject[2].getJSONArray("drinks");
                    //Products.length()
                    for (int idx = 0; idx < 10; idx++) {
                        // Get single Product data - a Map
                        JSONObject Product = (JSONObject) Products.get(idx);
                        // Delete HTML tags from description
                        //String description = Jsoup.parse(Product.get(DESCRIPTION_TAG).toString()).text();
                        // Build Product object
                        price = r.nextInt(30);
                        nombreProduct = Product.get("strDrink").toString();
                        Product ProductObj = new Product(nombreProduct, (float) price);

                        Drinks_list.add(ProductObj);
                        Log.v("Nombre producto= ", nombreProduct);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            setProduct_list(Product_list);
            setListDessert(Dessert_list);
            setListDrinks(Drinks_list);
            return Product_list;
        }
    }
}
