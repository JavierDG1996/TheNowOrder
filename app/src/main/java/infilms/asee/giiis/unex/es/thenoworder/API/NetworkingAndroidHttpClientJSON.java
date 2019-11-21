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

import infilms.asee.giiis.unex.es.thenoworder.NewOrderTabbedActivity;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;


public class NetworkingAndroidHttpClientJSON {

    private List<Product> product_list;
    private List<Product> listDessert;
    private List<Product> listDrinks;
    public NewOrderTabbedActivity newOrder;

    public NetworkingAndroidHttpClientJSON(NewOrderTabbedActivity newOrder) {
        new HttpGetTaskDrink().execute();
        new HttpGetTaskFood().execute();
        new HttpGetTaskDessert().execute();
        this.newOrder = newOrder;
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

    class HttpGetTaskFood extends AsyncTask<Void, Void, List<Product>> {
        private static final String BASE_URL = "api.spoonacular.com";
        private static final String JSON_SEG = "recipes";
        private static final String JSON_SEG2 = "random";
        private static final String Number = "number";
        private static final String apiKey = "apiKey";
        private static final String apiKeyValue = "f3bba258c5414ebc9083b7241491d522";


        @Override
        protected List<Product> doInBackground(Void... params) {
            URL queryURL;
            JSONObject result;

            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{JSON_SEG, JSON_SEG2},
                    new Pair(Number, "5"),
                    new Pair(apiKey, apiKeyValue));
            result = NetworkUtils.getJSONResponse(queryURL);

            if (result != null) {
                //Log.v("Lista de Recetas", "Getting response from the API");
                return jsonToList(result);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> FoodList) {
            super.onPostExecute(FoodList);
            setProduct_list(FoodList);
            newOrder.loadList(1);
            for (Product e : FoodList) {
                Log.v("Lista de comidas", "API: " + e.getProduct_name());
            }
        }

        public List<Product> jsonToList(JSONObject responseObject) {
            List<Product> Product_list = new ArrayList<>();

            Random r = new Random();
            int price;
            String nameProduct;
            JSONArray Products;
            try {

                Products = responseObject.getJSONArray("recipes");

                //JSON de comida a Lista de comida
                for (int idx = 0; idx < Products.length(); idx++) {
                    // Get single Product data - a Map
                    JSONObject Product = (JSONObject) Products.get(idx);
                    price = r.nextInt(30);
                    nameProduct = Product.get("title").toString();
                    Product ProductObj = new Product(nameProduct, (float) price);

                    Product_list.add(ProductObj);
                    //Log.v("Nombre producto= ", nameProduct);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Product_list;
        }
    }


    class HttpGetTaskDessert extends AsyncTask<Void, Void, List<Product>> {
        private static final String BASE_URL = "api.spoonacular.com";
        private static final String JSON_SEG = "recipes";
        private static final String JSON_SEG2 = "random";
        private static final String Number = "number";
        private static final String apiKey = "apiKey";
        private static final String apiKeyValue = "f3bba258c5414ebc9083b7241491d522";
        private static final String tipo = "type";
        private static final String postre = "dessert";

        @Override
        protected List<Product> doInBackground(Void... params) {
            URL queryURL;
            JSONObject result;

            queryURL = NetworkUtils.buildURL(BASE_URL,
                    new String[]{JSON_SEG, JSON_SEG2},
                    new Pair(tipo, postre),
                    new Pair(Number, "5"),
                    new Pair(apiKey, apiKeyValue));
            result = NetworkUtils.getJSONResponse(queryURL);

            if (result != null) {
                //Log.v("Lista de Postres", "Getting response from the API");
                return jsonToList(result);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> DessertList) {
            super.onPostExecute(DessertList);
            setListDessert(DessertList);
            newOrder.loadList(2);
            for (Product e : DessertList) {
                Log.v("Lista de postres", "API: " + e.getProduct_name());
            }

        }

        public List<Product> jsonToList(JSONObject responseObject) {
            List<Product> Dessert_list = new ArrayList<>();

            Random r = new Random();
            int price;
            String nameProduct;
            JSONArray Products;
            try {


                Products = responseObject.getJSONArray("recipes");
                for (int idx = 0; idx < Products.length(); idx++) {
                    // Get single Product data - a Map
                    JSONObject Product = (JSONObject) Products.get(idx);
                    price = r.nextInt(30);
                    nameProduct = Product.get("title").toString();
                    Product ProductObj = new Product(nameProduct, (float) price);

                    Dessert_list.add(ProductObj);
                   // Log.v("Nombre producto= ", nameProduct);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Dessert_list;
        }
    }


    class HttpGetTaskDrink extends AsyncTask<Void, Void, List<Product>> {
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
            JSONObject result;

            queryURL = NetworkUtils.buildURL(BASE_URLdrink,
                    new String[]{path, path2, path3, path4, path5},
                    new Pair("a", param));
            result = NetworkUtils.getJSONResponse(queryURL);

            if (result != null) {
                //Log.v("Lista de bebidas", "Getting response from the API");
                return jsonToList(result);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> DrinksList) {
            super.onPostExecute(DrinksList);
            setListDrinks(DrinksList);
            newOrder.loadList(0);
            for (Product e : DrinksList) {
                Log.v("Lista de bebidas", "API: " + e.getProduct_name());
            }
        }

        public List<Product> jsonToList(JSONObject responseObject) {
            List<Product> Drinks_list = new ArrayList<>();
            Random r = new Random();
            int price;
            String nameProduct;
            JSONArray Products;
            try {

                Products = responseObject.getJSONArray("drinks");
                //Products.length()
                for (int idx = 0; idx < 10; idx++) {
                    // Get single Product data - a Map
                    JSONObject Product = (JSONObject) Products.get(idx);
                    price = r.nextInt(30);
                    nameProduct = Product.get("strDrink").toString();
                    Product ProductObj = new Product(nameProduct, (float) price);

                    Drinks_list.add(ProductObj);
                    //Log.v("Nombre producto= ", nameProduct);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return Drinks_list;
        }
    }
}
