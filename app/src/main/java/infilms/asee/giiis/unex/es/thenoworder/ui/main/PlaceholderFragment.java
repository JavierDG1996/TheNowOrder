package infilms.asee.giiis.unex.es.thenoworder.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.adapters.ProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;
import infilms.asee.giiis.unex.es.thenoworder.classes.ProductList;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;
import infilms.asee.giiis.unex.es.thenoworder.utilities.InjectorUtils;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private int numTab;
    private ProductViewModel productVM;

    private RecyclerView product_list_rv;
    private ProductAdapter productAdapter;
    private repositoryPtt r;

    //Almacenar los productos en el bundle a través de la variable PRODUCT_LIST
    static PlaceholderFragment newInstance(int num) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        //Guardar la posición actual de la pestaña
        bundle.putInt("Numtab",num);
        fragment.setArguments(bundle);
        fragment.setNumTab(num);
        return fragment;
    }

    private void setNumTab(int numTab) {
        this.numTab = numTab;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //Asignar la posición actual de la pestaña
        if(this.getArguments() != null){
            numTab=this.getArguments().getInt("Numtab");
        }
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_order_tabbed, container, false);
        this.product_list_rv = root.findViewById(R.id.rv_product_list_id);

        if(getContext() != null) {
            r = InjectorUtils.provideRepository(getContext());
        }
        LinearLayoutManager LLManager = new LinearLayoutManager(this.getContext());
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.product_list_rv.setLayoutManager(LLManager);
        this.product_list_rv.setAdapter(productAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ProductList productList = new ProductList();
        if(getActivity() != null) {
            productVM = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
        }
        //Deginir Adapter
        productAdapter = new ProductAdapter(this.getContext(), productList, this.getActivity());

        /* Definir observers, se definen:
         0 la lista de bebidas,
         1 la lista de comida y
         3 la lista de postres.
         Tambien se llama al metodo loadProduct si no hay ninguna lista cargada,
         esto solo se hace la primera vez
        */
        switch (numTab){

            case 0:
                this.productVM.getDrinkList().observe(this, drinkList -> {
                    if (drinkList != null) {
                        Log.v("ProductList", "Observer drinkList");
                        updateUI(drinkList);
                    }
                });
                if(productVM.getDrinkList().getValue() == null){
                    this.loadProducts();
                }
                break;
            case 1:
                this.productVM.getFoodList().observe(this, foodList -> {
                    if (foodList != null) {
                        Log.v("ProductList", "Observer foodList");
                        updateUI(foodList);
                    }
                });
                if(productVM.getFoodList().getValue() == null){
                    this.loadProducts();
                }
                break;
            case 2:
                this.productVM.getDessertList().observe(this, dessertList -> {
                    if (dessertList != null) {
                        Log.v("ProductList", "Observer dessertList");
                        updateUI(dessertList);
                    }
                });
                if(productVM.getDessertList().getValue() == null){
                    this.loadProducts();
                }
                break;
        }

    }
    /*
    * Sirve para pintar la lista que se le pase por parametros
    * Se usa cuando se produce algun cambio como la rotación
    * */
    private void updateUI(ProductList productList) {
        if (productList != null) {
            productAdapter.load(productList);
            product_list_rv.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
    }

    /*
    * Realiza llamadas a metodos asincronos que cargan los productos
    * Se hace la llamada a los distintos metodos asincronos dependiendo de la posición en la que se esté
    * 0 para bebidas
    * 1 para comidas
    * 2 para postres
    * Este metodo se ejecuta solo la primera vez cuando no hay ninguna lista mostrada
    * */
    private void loadProducts() {

        Log.v("Cargando Productos", "Executing loadProduct");
        switch (numTab) {
            case 0: {//Cargar bebidas
                new AsyncLoadDrink().execute();
                break;
            }
            case 1: {//Cargar Comidas
                new AsyncLoadFood().execute();
                break;
            }
            case 2: {//Cargar Postres
                new AsyncLoadDessert().execute();
                break;
            }
        }

    }


    /**
     * AsyncTask to load all the food product from the api
     */
    @SuppressLint("StaticFieldLeak")
    class AsyncLoadFood extends AsyncTask<Void, Void, ProductList> {

        @Override
        protected ProductList doInBackground(Void... voids) {
            //Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);
            if(getContext() != null){
                r = InjectorUtils.provideRepository(getContext());
            }else{
                return null;
            }
            //r = repositoryPtt.getInstance(getContext());
            return new ProductList(r.getFoodFromApi());
        }

        @Override
        protected void onPostExecute(ProductList items) {
            super.onPostExecute(items);

            Log.v(TAG, "Product list");
            for (Product e : items.getElements()) {
                Log.v(TAG, e.getProduct_name());
            }
            productVM.setFoodList(items);
        }
    }

    /**
     * AsyncTask to load all the Products from the api
     */
    @SuppressLint("StaticFieldLeak")
    class AsyncLoadDrink extends AsyncTask<Void, Void, ProductList> {

        @Override
        protected ProductList doInBackground(Void... voids) {
            //Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);
            if(getContext() != null){
                r = InjectorUtils.provideRepository(getContext());
            }else{
                return null;
            }
            return new ProductList(r.getDrinkFromApi());
        }

        @Override
        protected void onPostExecute(ProductList items) {
            super.onPostExecute(items);

            Log.v(TAG, "Product list");
            for (Product e : items.getElements()) {
                Log.v(TAG, e.getProduct_name());
            }

            productVM.setDrinkList(items);
        }
    }

    /**
     * AsyncTask to load all the Products from the api
     */
    @SuppressLint("StaticFieldLeak")
    class AsyncLoadDessert extends AsyncTask<Void, Void, ProductList> {

        @Override
        protected ProductList doInBackground(Void... voids) {
            //Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);
            if(getContext() != null){
                r = InjectorUtils.provideRepository(getContext());
            }else{
                return null;
            }
            return new ProductList(r.getDessertFromApi());
        }

        @Override
        protected void onPostExecute(ProductList items) {
            super.onPostExecute(items);

            Log.v(TAG, "Product list");
            for (Product e : items.getElements()) {
                Log.v(TAG, e.getProduct_name());
            }

            productVM.setDessertList(items);
        }
    }
}