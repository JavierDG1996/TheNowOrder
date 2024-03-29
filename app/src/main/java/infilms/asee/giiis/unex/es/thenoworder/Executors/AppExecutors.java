package infilms.asee.giiis.unex.es.thenoworder.Executors;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors instance;
    private final Executor diskIO;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO){
        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance(){
        if (instance == null){
            synchronized (LOCK){
                instance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        new MainThreadExecutor(),
                        Executors.newFixedThreadPool(3));
            }
        }

        return instance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }


    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());


        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
