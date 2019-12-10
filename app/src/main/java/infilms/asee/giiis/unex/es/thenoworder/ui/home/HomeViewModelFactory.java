package infilms.asee.giiis.unex.es.thenoworder.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final repositoryPtt mRepository;

    public HomeViewModelFactory(repositoryPtt repository) {
        this.mRepository = repository;
    }


    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new HomeViewModel(mRepository);
    }
}
