package infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class CreateOrderViewModel  extends ViewModel {


    private final repositoryPtt mRepository;
    private final Order order;

    public CreateOrderViewModel(repositoryPtt repository, Order order) {
        this.mRepository = repository;
        this.order = order;
    }



    public void addOrder(){
        mRepository.addOrder(order);
    }



    /*public LiveData<Order> getOrder() {
        return this.order;
    }*/
}