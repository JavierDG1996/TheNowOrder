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
import infilms.asee.giiis.unex.es.thenoworder.roomDatabase.ProductDao;
import infilms.asee.giiis.unex.es.thenoworder.ui.settings.SettingsFragment;

public class repositoryPtt {
    private static final Object LOCK = new Object();
    private static repositoryPtt instance;

    private final OrderDao mOrderDao;
    private final ProductDao mProductDao;
    private final AppExecutors mAppExecutors;

    private static final String LOAD_FoodList = "FoodList";
    private static final String LOAD_DrinkList = "DrinkList";
    private static final String LOAD_DessertList = "DessertList";

    private NetworkingAndroidHttpClientJSON api;

    private MutableLiveData<List<Product>> FoodList;
    private MutableLiveData<List<Product>> DrinkList;
    private MutableLiveData<List<Product>> DessertList;

    private boolean mInitialized = false;


    /**
     * Private constructor of AppRepository class. Its a must in a singleton pattern.
     *
     * @param c Activity context from where we are instantiating the repository
     */
    private repositoryPtt(Context c, OrderDao mOrderDao, ProductDao mProductDao, AppExecutors mAppExecutors){

        this.mOrderDao = mOrderDao;
        this.mProductDao = mProductDao;
        this.mAppExecutors = mAppExecutors;
        api= new NetworkingAndroidHttpClientJSON(this.mAppExecutors);
        this.FoodList = new MutableLiveData<>();
        this.DrinkList = new MutableLiveData<>();
        this.DessertList = new MutableLiveData<>();

        //FoodList.observeForever();

        /*this.mAppExecutors.getNetworkIO().execute(() -> {
            this.FoodList.postValue(getFoodFromApi());
            this.DrinkList.postValue(getDrinkFromApi());
            this.DessertList.postValue(getDessertFromApi());
        });*/

        LiveData<List<Product>> food = api.getFood_list();
        food.observeForever(newFood -> {
            mAppExecutors.getDiskIO().execute(() -> {
                mProductDao.deleteFoodProducts();

                mProductDao.bulkInsert(newFood);
            });
        });

        LiveData<List<Product>> drinks = api.getListDrinks();
        drinks.observeForever(newDrinks -> {
            mAppExecutors.getDiskIO().execute(() -> {
                mProductDao.deleteDrinkProducts();

                mProductDao.bulkInsert(newDrinks);
            });
        });

        LiveData<List<Product>> desserts = api.getListDessert();
        desserts.observeForever(newDesserts -> {
            mAppExecutors.getDiskIO().execute(() -> {
                mProductDao.deleteDessertProducts();

                mProductDao.bulkInsert(newDesserts);
            });
        });


    }

    /**
     * Singleton main class. Allow us to work with just one instance of the repository.
     *
     * @param c Activity context from where we are instantiating the repository
     * @return Instance of repository
     */
    public static repositoryPtt getInstance(Context c, OrderDao mOrderDao, ProductDao mProductDao, AppExecutors mAppExecutors){
        if(instance == null){
            synchronized (LOCK) {
                instance = new repositoryPtt(c.getApplicationContext(), mOrderDao,mProductDao, mAppExecutors);
            }
        }
        return instance;
    }


    private synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        // This method call triggers Sunshine to create its task to synchronize weather data
        // periodically.
        //mWeatherNetworkDataSource.scheduleRecurringFetchWeatherSync();

        mAppExecutors.getDiskIO().execute(() -> {
            //if (isFetchNeeded()) {
                api.loadFood();
                api.loadDessert();
                api.loadDrink();
            //}
        });
    }
    /**
     * GET all the foods from the API
     *
     * @return List containing all the food from the API
     */
    private List<Product> getFoodFromApi() {
        List<Product> foods = new ArrayList<>();
        if (api.getFood_list() != null) {
            //foods = api.getFood_list();

            Log.v(LOAD_FoodList, "Getting foods from the API");
            for (Product e : foods) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return foods;
    }

    public LiveData<List<Product>> getFoodProductList(){
        //return this.FoodList;
        initializeData();
        return mProductDao.getFoodProducts();
    }

    /**
     * GET all the drinks from the API
     *
     * @return List containing all the drinks from the API
     */
    private List<Product> getDrinkFromApi() {
        List<Product> drinks = new ArrayList<>();
        if (api.getListDrinks() != null) {
            //drinks = api.getListDrinks();

            Log.v(LOAD_DrinkList, "Getting drinks from the API");
            for (Product e : drinks) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return drinks;
    }

    public LiveData<List<Product>> getDrinkProductList(){
        //return this.DrinkList;
        initializeData();
        return mProductDao.getDrinkProducts();
    }

    /**
     * GET all the desserts from the API
     *
     * @return List containing all the desserts from the API
     */
    private List<Product> getDessertFromApi() {
        List<Product> desserts = new ArrayList<>();
        if (api.getListDessert() != null) {
            //desserts = api.getListDessert();

            Log.v(LOAD_DessertList, "Getting desserts from the API");
            for (Product e : desserts) {
                Log.v("repositoryPtt", "Repository: " + e.getProduct_name());
            }

        }
        return desserts;
    }

    public LiveData<List<Product>> getDessertProductList(){
        //return this.DessertList;
        initializeData();
        return mProductDao.getDessertProducts();
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
