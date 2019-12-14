package infilms.asee.giiis.unex.es.thenoworder.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.adapters.OrderAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.utilities.InjectorUtils;


public class GalleryFragment extends Fragment {

    private List<Order> orderList;
    private RecyclerView order_list_rv;
    private int mPosition = RecyclerView.NO_POSITION;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        this.order_list_rv = root.findViewById(R.id.rv_paid_order_list_id);
        OrderAdapter O_adapter = new OrderAdapter(this.orderList,this.getContext(),true);
        LinearLayoutManager LLManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false);
        this.order_list_rv.setLayoutManager(LLManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        this.order_list_rv.setHasFixedSize(true);

        this.order_list_rv.setAdapter(O_adapter);

        GalleryViewModelFactory factory = InjectorUtils.provideGalleryViewModelFactory(this.getContext());
        GalleryViewModel galleryViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), factory).get(GalleryViewModel.class);

        galleryViewModel.getPaidOrders().observe(this, PaidOrders->{
            O_adapter.swapOrderList(PaidOrders);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            this.order_list_rv.smoothScrollToPosition(mPosition);

        });


        return root;
    }

}