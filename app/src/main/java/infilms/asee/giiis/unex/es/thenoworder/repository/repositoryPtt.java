package infilms.asee.giiis.unex.es.thenoworder.repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import infilms.asee.giiis.unex.es.thenoworder.API.NetworkingAndroidHttpClientJSON;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

public class repositoryPtt {
    private static repositoryPtt instance;

    private static final String LOAD_FoodList = "FoodList";
    private static final String LOAD_DrinkList = "DrinkList";
    private static final String LOAD_DessertList = "DessertList";

    private NetworkingAndroidHttpClientJSON api;

    /**
     * Private constructor of AppRepository class. Its a must in a singleton pattern.
     *
     * @param c Activity context from where we are instantiating the repository
     */
    private repositoryPtt(Context c){
        api= new NetworkingAndroidHttpClientJSON();
    }

    /**
     * Singleton main class. Allow us to work with just one instance of the repository.
     *
     * @param c Activity context from where we are instantiating the repository
     * @return Instance of repository
     */
    public static repositoryPtt getInstance(Context c){
        if(instance == null){
            instance= new repositoryPtt(c.getApplicationContext());
        }
        return instance;
    }

    /**
     * GET all the foods from the API
     *
     * @return List containing all the food from the API
     */
    public List<Product> getFoodFromApi() {
        List<Product> foods = new ArrayList<>();
        if (api.getFood_list() != null) {
            foods = api.getFood_list().getElements();

            Log.v(LOAD_FoodList, "Getting foods from the API");
            for (Product e : foods) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return foods;
    }

    /**
     * GET all the drinks from the API
     *
     * @return List containing all the drinks from the API
     */
    public List<Product> getDrinkFromApi() {
        List<Product> drinks = new ArrayList<>();
        if (api.getListDrinks() != null) {
            drinks = api.getListDrinks().getElements();

            Log.v(LOAD_DrinkList, "Getting drinks from the API");
            for (Product e : drinks) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return drinks;
    }

    /**
     * GET all the desserts from the API
     *
     * @return List containing all the desserts from the API
     */
    public List<Product> getDessertFromApi() {
        List<Product> desserts = new ArrayList<>();
        if (api.getListDessert() != null) {
            desserts = api.getListDessert().getElements();

            Log.v(LOAD_DessertList, "Getting desserts from the API");
            for (Product e : desserts) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return desserts;
    }
}
