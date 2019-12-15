package infilms.asee.giiis.unex.es.thenoworder.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.AppRepository;

public class HomeViewModel extends ViewModel {

    private final LiveData<List<Order>> PendentOrders;

    public HomeViewModel(AppRepository repository) {
        PendentOrders = repository.getAllPendentOrders();
    }

    public LiveData<List<Order>> getPendentOrders() {
        return PendentOrders;
    }
}