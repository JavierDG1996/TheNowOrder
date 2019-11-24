package infilms.asee.giiis.unex.es.thenoworder.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Opción próximamente disponible");
    }

    public LiveData<String> getText() {
        return mText;
    }
}