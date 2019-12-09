package infilms.asee.giiis.unex.es.thenoworder.ui.home;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class HomeViewModel extends ViewModel {

    private final repositoryPtt mRepository;
    private final LiveData<List<Order>> PendentOrders;

    public HomeViewModel(repositoryPtt repository) {
        this.mRepository = repository;
        PendentOrders = mRepository.getAllPendentOrders();
    }

    public LiveData<List<Order>> getPendentOrders() {
        return PendentOrders;
    }
}