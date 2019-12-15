package infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder;

import androidx.lifecycle.ViewModel;

import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

class CreateOrderViewModel  extends ViewModel {


    private final repositoryPtt mRepository;
    private final Order order;

    CreateOrderViewModel(repositoryPtt repository, Order order) {
        this.mRepository = repository;
        this.order = order;
    }



    void addOrder(){
        mRepository.addOrder(order);
    }
}