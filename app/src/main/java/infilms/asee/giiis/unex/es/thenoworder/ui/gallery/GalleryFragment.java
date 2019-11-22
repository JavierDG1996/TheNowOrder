package infilms.asee.giiis.unex.es.thenoworder.ui.gallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.adapters.OrderAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.roomDatabase.AppDatabase;
import infilms.asee.giiis.unex.es.thenoworder.ui.home.HomeFragment;

public class GalleryFragment extends Fragment {

    private List<Order> orderList;
    private RecyclerView order_list_rv;

    /*private GalleryViewModel galleryViewModel;  //Para la siguiente entrega NO BORRAR

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        loadOrders();

        this.order_list_rv = root.findViewById(R.id.rv_paid_order_list_id);
        OrderAdapter O_adapter = new OrderAdapter(this.orderList,this.getContext(),true);
        LinearLayoutManager LLManager = new LinearLayoutManager(this.getContext());
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.order_list_rv.setLayoutManager(LLManager);
        this.order_list_rv.setAdapter(O_adapter);


        return root;
    }


    public void loadOrders(){
        try {
            this.orderList = new GalleryFragment.getAllOrders().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** ASYNC TASKS **/
    /*1. Get All orders*/
    class getAllOrders extends AsyncTask<Void, Void, List<Order>> {

        @Override
        protected List<Order> doInBackground(Void... voids) {
            AppDatabase database = AppDatabase.getDatabase(getActivity());
            List<Order> items = database.orderDao().getAllPaidOrders();
            return items;
        }
    }
}