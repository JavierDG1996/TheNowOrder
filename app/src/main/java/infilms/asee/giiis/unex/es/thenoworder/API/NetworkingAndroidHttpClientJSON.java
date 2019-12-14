package infilms.asee.giiis.unex.es.thenoworder.API;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import infilms.asee.giiis.unex.es.thenoworder.Executors.AppExecutors;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;


public class NetworkingAndroidHttpClientJSON {

    private List<Product> foodList;
    private List<Product> drinkList;
    private List<Product> dessertList;

    //  Variables para soopnacular

    private static final String BASE_URLspoonacular = "api.spoonacular.com";
    private static final String JSON_SEG = "recipes";
    private static final String JSON_SEG2 = "random";
    private static final String Number = "number";
    private static final String apiKey = "apiKey";
    private static final String apiKeyValue = "f3bba258c5414ebc9083b7241491d522";
    private static final String apiKeyValueAux = "c6d8b6b3b2854d55af9d8154311ebaa5";
    private static final String tipo = "type";
    private static final String postre = "dessert";

    //Variables para thecocktaildb

    private static final String BASE_URLdrink = "www.thecocktaildb.com";
    private static final String path = "api";
    private static final String path2 = "json";
    private static final String path3 = "v1";
    private static final String path4 = "1";
    private static final String path5 = "filter.php";
    private static final String param = "Non_Alcoholic";

    /*
    * public builder
    * Initialize product lists
    * Ejecutar con el hilo de NetworkIO los metodos que obtienen los productos de la API
    */
    public NetworkingAndroidHttpClientJSON(AppExecutors mAppExecutors) {
        foodList= new ArrayList<>();
        drinkList= new ArrayList<>();
        dessertList= new ArrayList<>();

        mAppExecutors.getNetworkIO().execute(() -> {
            this.loadFood();
            this.loadDessert();
            this.loadDrink();
        });
    }

    /*
     * GET the foods list
     *
     * @return List containing all the foods
     */
    public List<Product> getFood_list() {
        return foodList;
    }

    /*
     * GET the desserts list
     *
     * @return List containing all the desserts
     */
    public List<Product> getListDessert() {
        return dessertList;
    }

    /*
     * GET the drinks list
     *
     * @return List containing all the drinks
     */
    public List<Product> getListDrinks() {
        return drinkList;
    }

    /*
     * Cargar las Comidas *****************************************************************
     * */

    /*
     * Construir la utl para la API soopnacular para sacar las comidas
     * */
    private URL getURLFood(String connectionApiKey){
        return NetworkUtils.buildURL(BASE_URLspoonacular,
                new String[]{JSON_SEG, JSON_SEG2},
                new Pair<>(Number, "15"),
                new Pair<>(apiKey, connectionApiKey));
    }

    /*
    * Llama para construir la URL con dos API key
    * Se llama al construir URL con la primera API key si falla se llama con la
    * API key auxiliar
    * Con el objeto que se devuelve (JSON) se llama al metodo jsonToListFood
    * para convertir JSON a Lista
    * */
    private void loadFood() {
        URL queryURL;
        JSONObject result;

        queryURL= getURLFood(apiKeyValue);
        result = NetworkUtils.getJSONResponse(queryURL);

        if(result == null){
            queryURL= getURLFood(apiKeyValueAux);
            result = NetworkUtils.getJSONResponse(queryURL);
        }

        if (result != null) {
            jsonToListFood(result);
        }
    }

    /*
    * Recibe un JSON y se va recorriendo, y se extrae los productos
    * que se usaran para rellenar la lista de comidas
    * */
    private void jsonToListFood(JSONObject responseObject) {

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

                foodList.add(ProductObj);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
     * Cargar los Postres *****************************************************************
     * */

    /*
     * Construir la utl para la API soopnacular para sacar los postres
     * */
    private URL getURLDessert(String ConnectApiKey){
        return NetworkUtils.buildURL(BASE_URLspoonacular,
                new String[]{JSON_SEG, JSON_SEG2},
                new Pair<>(tipo, postre),
                new Pair<>(Number, "15"),
                new Pair<>(apiKey, ConnectApiKey ));
    }

    /*
     * Llama para construir la URL con dos API key
     * Se llama al construir URL con la primera API key si falla se llama con la
     * API key auxiliar
     * Con el objeto que se devuelve (JSON) se llama al metodo jsonToListFood
     * para convertir JSON a Lista
     * */
    private void loadDessert() {
        URL queryURL;
        JSONObject result;

        queryURL=getURLDessert(apiKeyValue);
        result = NetworkUtils.getJSONResponse(queryURL);

        if(result == null){
            queryURL= getURLFood(apiKeyValueAux);
            result = NetworkUtils.getJSONResponse(queryURL);
        }

        if (result != null) {
            jsonToListDessert(result);
        }
    }

    /*
     * Recibe un JSON y se va recorriendo, y se extrae los productos
     * que se usaran para rellenar la lista de postres
     * */
    private void jsonToListDessert(JSONObject responseObject) {

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

                dessertList.add(ProductObj);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*
    * Cargar las bebidas *****************************************************************
    * */


    /*
     * Construir la utl para la API thecocktaildb para sacar las bebidas
     * */
   private void loadDrink() {
        URL queryURL;
        JSONObject result;

        queryURL = NetworkUtils.buildURL(BASE_URLdrink,
                new String[]{path, path2, path3, path4, path5},
                new Pair<>("a", param));
        result = NetworkUtils.getJSONResponse(queryURL);

        if (result != null) {
            //Log.v("Lista de bebidas", "Getting response from the API");
            jsonToListDrink(result);
        }
    }

    /*
     * Recibe un JSON y se va recorriendo, y se extrae los productos
     * que se usaran para rellenar la lista de bebidas
     * */
    private void jsonToListDrink(JSONObject responseObject) {
        Random r = new Random();
        int price;
        String nameProduct;
        JSONArray Products;
        try {

            Products = responseObject.getJSONArray("drinks");
            //Products.length()
            for (int idx = 0; idx < 15; idx++) {
                // Get single Product data - a Map
                JSONObject Product = (JSONObject) Products.get(idx);
                price = r.nextInt(30);
                nameProduct = Product.get("strDrink").toString();
                Product ProductObj = new Product(nameProduct, (float) price);

                drinkList.add(ProductObj);
                //Log.v("Nombre producto= ", nameProduct);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

