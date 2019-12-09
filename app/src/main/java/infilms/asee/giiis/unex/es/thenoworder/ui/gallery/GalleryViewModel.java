package infilms.asee.giiis.unex.es.thenoworder.ui.gallery;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class GalleryViewModel extends ViewModel {

    private final repositoryPtt mRepository;
    private final LiveData<List<Order>> PaidOrders;

    public GalleryViewModel(repositoryPtt repository) {
        this.mRepository = repository;
        PaidOrders = mRepository.getAllPaidOrders();
    }

    public LiveData<List<Order>> getPaidOrders() {
        return PaidOrders;
    }


}