package infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.AppRepository;

public class SummaryOrderViewModel extends ViewModel {


    private final AppRepository mRepository;
    private final LiveData<Order> order;

    SummaryOrderViewModel(AppRepository repository, long order_id) {
        this.mRepository = repository;
        this.order = mRepository.getOrderById(order_id);
    }

    public void updateOrder(){
        mRepository.updateOrder(order.getValue());
    }

    public void deleteOrder(){
        mRepository.deleteOrder(order.getValue());
    }

    public LiveData<Order> getOrder() {
        return this.order;
    }
}
