package infilms.asee.giiis.unex.es.thenoworder.ui.gallery;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.AppRepository;

class GalleryViewModel extends ViewModel {

    private final LiveData<List<Order>> PaidOrders;

    GalleryViewModel(AppRepository repository) {
        PaidOrders = repository.getAllPaidOrders();
    }

    LiveData<List<Order>> getPaidOrders() {
        return PaidOrders;
    }


}