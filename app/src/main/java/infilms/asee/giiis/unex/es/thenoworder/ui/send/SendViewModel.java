package infilms.asee.giiis.unex.es.thenoworder.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Opción próximamente disponible");
    }

    public LiveData<String> getText() {
        return mText;
    }
}