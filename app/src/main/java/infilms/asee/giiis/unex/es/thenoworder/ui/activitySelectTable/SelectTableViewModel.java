package infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable;

import android.content.Context;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.ViewModel;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class SelectTableViewModel extends ViewModel {

    private final repositoryPtt mRepository;
    private LiveData<Integer> mesas;

    public SelectTableViewModel(Context context, repositoryPtt repository) {
        this.mRepository = repository;

        this.mesas = mRepository.getTables(context);
    }

    public LiveData<Integer> getTables() {
        return this.mesas;
    }
}
