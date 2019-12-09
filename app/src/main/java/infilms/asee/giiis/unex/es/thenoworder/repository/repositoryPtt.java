package infilms.asee.giiis.unex.es.thenoworder.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import infilms.asee.giiis.unex.es.thenoworder.API.NetworkingAndroidHttpClientJSON;
import infilms.asee.giiis.unex.es.thenoworder.Executors.AppExecutors;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;
import infilms.asee.giiis.unex.es.thenoworder.roomDatabase.OrderDao;
import infilms.asee.giiis.unex.es.thenoworder.ui.settings.SettingsFragment;

public class repositoryPtt {
    private static final Object LOCK = new Object();
    private static repositoryPtt instance;

    private final OrderDao mOrderDao;
    private final AppExecutors mAppExecutors;

    private static final String LOAD_FoodList = "FoodList";
    private static final String LOAD_DrinkList = "DrinkList";
    private static final String LOAD_DessertList = "DessertList";

    private NetworkingAndroidHttpClientJSON api;

    /**
     * Private constructor of AppRepository class. Its a must in a singleton pattern.
     *
     * @param c Activity context from where we are instantiating the repository
     */
    private repositoryPtt(Context c, OrderDao mOrderDao, AppExecutors mAppExecutors){
        api= new NetworkingAndroidHttpClientJSON();
        this.mOrderDao = mOrderDao;
        this.mAppExecutors = mAppExecutors;
    }

    /**
     * Singleton main class. Allow us to work with just one instance of the repository.
     *
     * @param c Activity context from where we are instantiating the repository
     * @return Instance of repository
     */
    public static repositoryPtt getInstance(Context c, OrderDao mOrderDao, AppExecutors mAppExecutors){
        if(instance == null){
            synchronized (LOCK) {
                instance = new repositoryPtt(c.getApplicationContext(), mOrderDao, mAppExecutors);
            }
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

    /**
     * Preference operation
     * @param context
     * @return number of tables
     */

    public LiveData<Integer> getTables(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String mesas_string = sharedPreferences.getString(SettingsFragment.KEY_PREF_MESA,"0");

        MutableLiveData<Integer> mesas = new MutableLiveData<>();
        mesas.setValue(Integer.parseInt(mesas_string));

        return mesas;
    }


    /**
     * Database related operations
     **/

    public LiveData<List<Order>> getAllPendentOrders() {
        //initializeData();
        return mOrderDao.getAllPendentOrders();
    }

    public LiveData<List<Order>> getAllPaidOrders() {
        //initializeData();
        return mOrderDao.getAllPaidOrders();
    }

    public void deleteOrder(Order order){
        mAppExecutors.getDiskIO().execute(()->{
            mOrderDao.deleteOrder(order);
        });
    }

    public void updateOrder(Order order){
        mAppExecutors.getDiskIO().execute(()->{
            mOrderDao.updateOrder(order);
        });
    }

    public void addOrder(Order order){
        mAppExecutors.getDiskIO().execute(()->{
            mOrderDao.addOrder(order);
        });
    }

}
