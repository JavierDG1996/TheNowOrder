package infilms.asee.giiis.unex.es.thenoworder.ui.gallery;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class GalleryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final repositoryPtt mRepository;

    public GalleryViewModelFactory(repositoryPtt repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GalleryViewModel(mRepository);
    }
}
