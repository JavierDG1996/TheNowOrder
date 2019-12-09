package infilms.asee.giiis.unex.es.thenoworder.ViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class SummaryOrderViewModel extends ViewModel {


    private final repositoryPtt mRepository;
    private final long order_id;
    private final LiveData<Order> order;

    public SummaryOrderViewModel(repositoryPtt repository, long order_id) {
        this.mRepository = repository;
        this.order_id = order_id;
        this.order = mRepository.getOrderById(order_id);
    }

    public LiveData<Order> getOrder() {
        return this.order;
    }
}
