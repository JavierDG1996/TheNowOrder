package infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable.SelectTableViewModel;

public class SelectTableViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final repositoryPtt mRepository;
    private final Context context;

    public SelectTableViewModelFactory(Context context, repositoryPtt repository) {
        this.mRepository = repository;
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new SelectTableViewModel(context, mRepository);
    }
}
