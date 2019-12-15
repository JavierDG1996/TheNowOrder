package infilms.asee.giiis.unex.es.thenoworder.ui.main;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.adapters.ProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;
import infilms.asee.giiis.unex.es.thenoworder.utilities.InjectorUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private int numTab;
    private ProductViewModel productVM;

    private RecyclerView product_list_rv;
    private ProductAdapter productAdapter;

    //Almacenar los productos en el bundle a través de la variable PRODUCT_LIST
    public static PlaceholderFragment newInstance(int num) {
        PlaceholderFragment fragment = new PlaceholderFragment();

        Bundle bundle = new Bundle();
        //Guardar la posición actual de la pestaña
        bundle.putInt("Numtab",num);
        fragment.setArguments(bundle);

        return fragment;
    }


    /*
    * Cambia el valor de la varaible numTab
    * Numtan indica cual es la posición de la instancia en
    * las pestañas
    * */
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
            ProductViewModelFactory factory = InjectorUtils.provideProductViewModelFactory(getContext());
            this.productVM = ViewModelProviders.of(Objects.requireNonNull(getActivity()), factory).get(ProductViewModel.class);
        }
        LinearLayoutManager LLManager = new LinearLayoutManager(this.getContext());
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.product_list_rv.setLayoutManager(LLManager);
        this.product_list_rv.setAdapter(productAdapter);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        List<Product> productList = new ArrayList<>();

        //Definir Adapter
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
                break;
            case 1:
                this.productVM.getFoodList().observe(this, foodList -> {
                    if (foodList != null) {
                        Log.v("ProductList", "Observer foodList");
                        updateUI(foodList);
                    }
                });
                break;
            case 2:
                this.productVM.getDessertList().observe(this, dessertList -> {
                    if (dessertList != null) {
                        Log.v("ProductList", "Observer dessertList");
                        updateUI(dessertList);
                    }
                });
                break;
        }

    }


    /*
    * Sirve para pintar la lista que se le pase por parametros
    * Se usa cuando se produce algun cambio como la rotación
    * */
    //private void updateUI(ProductList productList) {
    private void updateUI(List<Product> productList) {
        if (productList != null) {
            productAdapter.load(productList);
            product_list_rv.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
    }
}