package infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

class SelectTableViewModel extends ViewModel {

    private LiveData<Integer> mesas;

    SelectTableViewModel(Context context, repositoryPtt repository) {

        this.mesas = repository.getTables(context);
    }

    LiveData<Integer> getTables() {
        return this.mesas;
    }
}
