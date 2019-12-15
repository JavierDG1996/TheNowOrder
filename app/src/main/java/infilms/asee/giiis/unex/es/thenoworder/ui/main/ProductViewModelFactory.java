package infilms.asee.giiis.unex.es.thenoworder.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import infilms.asee.giiis.unex.es.thenoworder.repository.AppRepository;

public class ProductViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppRepository mRepository;

    public ProductViewModelFactory(AppRepository repository) {
        this.mRepository = repository;
    }


    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ProductViewModel(mRepository);
    }
}
