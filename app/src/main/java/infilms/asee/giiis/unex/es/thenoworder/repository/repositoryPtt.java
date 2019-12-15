package infilms.asee.giiis.unex.es.thenoworder.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private Context mContext;

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



    /**
     * Private constructor of AppRepository class. Its a must in a singleton pattern.
     *
     * @param c Activity context from where we are instantiating the repository
     */
    private repositoryPtt(Context c, OrderDao mOrderDao, ProductDao mProductDao, AppExecutors mAppExecutors){

        this.mContext = c;
        this.mOrderDao = mOrderDao;
        this.mProductDao = mProductDao;
        this.mAppExecutors = mAppExecutors;
        api= new NetworkingAndroidHttpClientJSON(this.mAppExecutors);
        this.FoodList = new MutableLiveData<>();
        this.DrinkList = new MutableLiveData<>();
        this.DessertList = new MutableLiveData<>();

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


    //This method is used to get the products from the api one time only
    private synchronized void initializeData() {

        //Utilizando las SharedPreferences podemos controlar que los productos de la api solo se carguen una vez en toda la aplicación.
        //El valor  del booleano será falso la primera vez y cuando entre en el código del método será verdadero por lo que ese código solo se ejecutará una vez.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean mLocked = sharedPreferences.getBoolean("Pref_One_Charge",false);

        if (mLocked) return;
        sharedPreferences.edit().putBoolean("Pref_One_Charge",true).apply();


        mAppExecutors.getDiskIO().execute(() -> {
            api.loadFood();
            api.loadDessert();
            api.loadDrink();
        });
    }

    //Get food type products
    public LiveData<List<Product>> getFoodProductList(){
        initializeData();
        return mProductDao.getFoodProducts();
    }


    //Get drink type products
    public LiveData<List<Product>> getDrinkProductList(){
        initializeData();
        return mProductDao.getDrinkProducts();
    }


    //Get dessert type products
    public LiveData<List<Product>> getDessertProductList(){
        initializeData();
        return mProductDao.getDessertProducts();
    }

    /**
     * Preference operation to get the number of the tables according to settings.
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

    //Get all not paid Orders from the database
    public LiveData<List<Order>> getAllPendentOrders() {
        return mOrderDao.getAllPendentOrders();
    }

    //Get all paid Orders from the database
    public LiveData<List<Order>> getAllPaidOrders() {
        return mOrderDao.getAllPaidOrders();
    }

    //Get an order with an id from the database
    public LiveData<Order> getOrderById(long order_id) {
        return mOrderDao.getById(order_id);
    }

    //Delete the order from the database
    public void deleteOrder(Order order){
        mAppExecutors.getDiskIO().execute(()-> mOrderDao.deleteOrder(order));
    }

    //Update the order from the database
    public void updateOrder(Order order){
        mAppExecutors.getDiskIO().execute(()-> mOrderDao.updateOrder(order));
    }

    //Add the order to the database
    public void addOrder(Order order){
        mAppExecutors.getDiskIO().execute(()-> mOrderDao.addOrder(order));
    }

}
