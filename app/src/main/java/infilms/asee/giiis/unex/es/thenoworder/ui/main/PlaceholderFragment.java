package infilms.asee.giiis.unex.es.thenoworder.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.ViewModel.ProductViewModel;
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

    private static final String PRODUCT_LIST = "productList";
    private ProductList productList;
    private int numTab;
    private ProductViewModel productVM;

    private RecyclerView product_list_rv;
    ProductAdapter productAdapter;
    repositoryPtt r;

    //Almacenar los productos en el bundle a través de la variable PRODUCT_LIST
 /*   public static PlaceholderFragment newInstance(int num) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        numTab=num;
        return fragment;
    }
*/
    PlaceholderFragment(int num) {
        Bundle bundle = new Bundle();
        this.setArguments(bundle);
        numTab = num;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_order_tabbed, container, false);
        this.product_list_rv = root.findViewById(R.id.rv_product_list_id);

        r = InjectorUtils.provideRepository(getContext());
        //r = repositoryPtt.getInstance(getContext());

        LinearLayoutManager LLManager = new LinearLayoutManager(this.getContext());
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.product_list_rv.setLayoutManager(LLManager);
        this.product_list_rv.setAdapter(productAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.productList = new ProductList();

        productVM = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);

        //Deginir Adapter
        productAdapter = new ProductAdapter(this.getContext(), this.productList, this.getActivity());

        // Definir observer, se define para 0 la lista de bebidas, para uno la lista de comida y para 3 la lista de postres
        switch (numTab){
            case 0:
                this.productVM.getDrinkList().observe((LifecycleOwner) this, drinkList -> {
                    if (drinkList != null) {
                        Log.v("ProductList", "Observer drinkList");
                        updateUI(drinkList);
                    }
                });
                break;
            case 1:
                this.productVM.getFoodList().observe((LifecycleOwner) this, foodList -> {
                    if (foodList != null) {
                        Log.v("ProductList", "Observer foodList");
                        updateUI(foodList);
                    }
                });
                break;
            case 2:
                this.productVM.getDessertList().observe((LifecycleOwner) this, dessertList -> {
                    if (dessertList != null) {
                        Log.v("ProductList", "Observer dessertList");
                        updateUI(dessertList);
                    }
                });
                break;
        }

        this.loadProducts();
    }

    private void updateUI(ProductList productList) {
        if (productList != null) {
            productAdapter.load(productList);
            product_list_rv.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
    }

    public PlaceholderFragment myInstance() {
        return this;
    }

    private void loadProducts() {
        Log.v("Cargando Productos", "Executing loadProduct");
        switch (numTab) {
            case 0: {
                new AsyncLoadDrink().execute();
                break;
            }
            case 1: {
                new AsyncLoadFood().execute();
                break;
            }
            case 2: {
                new AsyncLoadDessert().execute();
                break;
            }
        }

    }


    /**
     * AsyncTask to load all the food product from the api
     */
    class AsyncLoadFood extends AsyncTask<Void, Void, ProductList> {

        @Override
        protected ProductList doInBackground(Void... voids) {
            //Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);
            r = InjectorUtils.provideRepository(getContext());
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
    class AsyncLoadDrink extends AsyncTask<Void, Void, ProductList> {

        @Override
        protected ProductList doInBackground(Void... voids) {
            //Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);
            r = InjectorUtils.provideRepository(getContext());
            //r = repositoryPtt.getInstance(getContext());
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
    class AsyncLoadDessert extends AsyncTask<Void, Void, ProductList> {

        @Override
        protected ProductList doInBackground(Void... voids) {
            //Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);
            r = InjectorUtils.provideRepository(getContext());
            //r = repositoryPtt.getInstance(getContext());
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