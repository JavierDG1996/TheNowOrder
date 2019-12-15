package infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import infilms.asee.giiis.unex.es.thenoworder.repository.AppRepository;

public class SelectTableViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppRepository mRepository;
    private final Context context;

    public SelectTableViewModelFactory(Context context, AppRepository repository) {
        this.mRepository = repository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new SelectTableViewModel(context, mRepository);
    }
}
