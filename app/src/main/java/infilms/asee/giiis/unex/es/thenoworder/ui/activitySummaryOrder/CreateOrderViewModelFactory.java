package infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder;

import androidx.annotation.NonNull;
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

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new CreateOrderViewModel(mRepository,order);
    }
}

