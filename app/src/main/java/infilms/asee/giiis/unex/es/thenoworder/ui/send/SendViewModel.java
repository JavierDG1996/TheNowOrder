package infilms.asee.giiis.unex.es.thenoworder.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mTextAbout;
    private MutableLiveData<String> mTextAuthor1;
    private MutableLiveData<String> mTextGmail1;
    private MutableLiveData<String> mTextAuthor2;
    private MutableLiveData<String> mTextGmail2;

    public SendViewModel() {
        mTextAbout = new MutableLiveData<>();
        mTextAbout.setValue("The Now Order fue creada por:");

        mTextAuthor1 = new MutableLiveData<>();
        mTextAuthor1.setValue("Javier Durán García");

        mTextGmail1 = new MutableLiveData<>();
        mTextGmail1.setValue("jadurang@alumnos.unex.es");

        mTextAuthor2 = new MutableLiveData<>();
        mTextAuthor2.setValue("Juan Francisco Martín Granado");

        mTextGmail2 = new MutableLiveData<>();
        mTextGmail2.setValue("jmartinfy@alumnos.unex.es");
    }

    public LiveData<String> getAbout() {
        return mTextAbout;
    }

    public LiveData<String> getAuthor_name_1() {
        return mTextAuthor1;
    }

    public LiveData<String> getAuthor_gmail_1() {
        return mTextGmail1;
    }

    public LiveData<String> getAuthor_name_2() {
        return mTextAuthor2;
    }

    public LiveData<String> getAuthor_gmail_2() {
        return mTextGmail2;
    }
}