package infilms.asee.giiis.unex.es.thenoworder.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

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

    private MutableLiveData<List<Product>> FoodList;
    private MutableLiveData<List<Product>> DrinkList;
    private MutableLiveData<List<Product>> DessertList;


    /**
     * Private constructor of AppRepository class. Its a must in a singleton pattern.
     *
     * @param c Activity context from where we are instantiating the repository
     */
    private repositoryPtt(Context c, OrderDao mOrderDao, AppExecutors mAppExecutors){
        api= new NetworkingAndroidHttpClientJSON(mAppExecutors);
        this.mOrderDao = mOrderDao;
        this.mAppExecutors = mAppExecutors;
        this.FoodList = new MutableLiveData<>();
        this.DrinkList = new MutableLiveData<>();
        this.DessertList = new MutableLiveData<>();


        this.mAppExecutors.getNetworkIO().execute(() -> {
            this.FoodList.postValue(getFoodFromApi());
            this.DrinkList.postValue(getDrinkFromApi());
            this.DessertList.postValue(getDessertFromApi());
        });


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
    private List<Product> getFoodFromApi() {
        List<Product> foods = new ArrayList<>();
        if (api.getFood_list() != null) {
            foods = api.getFood_list();

            Log.v(LOAD_FoodList, "Getting foods from the API");
            for (Product e : foods) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return foods;
    }

    public LiveData<List<Product>> getFoodProductList(){
        return this.FoodList;
    }

    /**
     * GET all the drinks from the API
     *
     * @return List containing all the drinks from the API
     */
    private List<Product> getDrinkFromApi() {
        List<Product> drinks = new ArrayList<>();
        if (api.getListDrinks() != null) {
            drinks = api.getListDrinks();

            Log.v(LOAD_DrinkList, "Getting drinks from the API");
            for (Product e : drinks) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return drinks;
    }

    public LiveData<List<Product>> getDrinkProductList(){
        return this.DrinkList;
    }

    /**
     * GET all the desserts from the API
     *
     * @return List containing all the desserts from the API
     */
    private List<Product> getDessertFromApi() {
        List<Product> desserts = new ArrayList<>();
        if (api.getListDessert() != null) {
            desserts = api.getListDessert();

            Log.v(LOAD_DessertList, "Getting desserts from the API");
            for (Product e : desserts) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return desserts;
    }

    public LiveData<List<Product>> getDessertProductList(){
        return this.DessertList;
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
        assert mesas_string != null;
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

    public LiveData<Order> getOrderById(long order_id) {
        //initializeData();
        return mOrderDao.getById(order_id);
    }

    public void deleteOrder(Order order){
        mAppExecutors.getDiskIO().execute(()-> mOrderDao.deleteOrder(order));
    }

    public void updateOrder(Order order){
        mAppExecutors.getDiskIO().execute(()-> mOrderDao.updateOrder(order));
    }

    public void addOrder(Order order){
        mAppExecutors.getDiskIO().execute(()-> mOrderDao.addOrder(order));
    }

}
