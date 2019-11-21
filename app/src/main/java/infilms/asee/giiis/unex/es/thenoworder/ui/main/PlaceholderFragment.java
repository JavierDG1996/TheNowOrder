package infilms.asee.giiis.unex.es.thenoworder.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.adapters.ProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String PRODUCT_LIST = "productList";
    private ArrayList<Product> productList;
    //private PageViewModel pageViewModel; //Se usará en la siguiente practica
    private RecyclerView product_list_rv;
    ProductAdapter P_adapter;

    //Almacenar los productos en el bundle a través de la variable PRODUCT_LIST
    public static PlaceholderFragment newInstance(List<Product> products) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCT_LIST, (Serializable) products);
        fragment.setArguments(bundle);
        return fragment;
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(PRODUCT_LIST);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_order_tabbed, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }*/

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_order_tabbed, container, false);

        this.productList = (ArrayList<Product>) getArguments().getSerializable(PRODUCT_LIST);

        this.product_list_rv = root.findViewById(R.id.rv_product_list_id);
        P_adapter = new ProductAdapter(this.getContext(), this.productList, this.getActivity());
        LinearLayoutManager LLManager = new LinearLayoutManager(this.getContext());
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.product_list_rv.setLayoutManager(LLManager);
        this.product_list_rv.setAdapter(P_adapter);

        return root;
    }

    public void UpdateData(ArrayList<Product> productList){
        P_adapter.load(productList);
        product_list_rv.setAdapter(P_adapter);
        P_adapter.notifyDataSetChanged();
    }

    public PlaceholderFragment myInstance(){
        return this;
    }
}