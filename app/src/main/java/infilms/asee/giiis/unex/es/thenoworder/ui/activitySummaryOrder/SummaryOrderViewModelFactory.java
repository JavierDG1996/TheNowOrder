package infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class SummaryOrderViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final repositoryPtt mRepository;
    private final long order_id;

    public SummaryOrderViewModelFactory(repositoryPtt repository, long order_id) {

        this.mRepository = repository;
        this.order_id = order_id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new SummaryOrderViewModel(mRepository,order_id);
    }
}
