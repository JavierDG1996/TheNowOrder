package infilms.asee.giiis.unex.es.thenoworder.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class CreateOrderViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final repositoryPtt mRepository;
    private final Order order;

    public CreateOrderViewModelFactory(repositoryPtt repository, Order order) {

        this.mRepository = repository;
        this.order = order;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new CreateOrderViewModel(mRepository,order);
    }
}

