package infilms.asee.giiis.unex.es.thenoworder.utilities;

import android.content.Context;

import infilms.asee.giiis.unex.es.thenoworder.Executors.AppExecutors;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder.CreateOrderViewModelFactory;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable.SelectTableViewModelFactory;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder.SummaryOrderViewModelFactory;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.AppRepository;
import infilms.asee.giiis.unex.es.thenoworder.roomDatabase.AppDatabase;
import infilms.asee.giiis.unex.es.thenoworder.ui.gallery.GalleryViewModelFactory;
import infilms.asee.giiis.unex.es.thenoworder.ui.home.HomeViewModelFactory;
import infilms.asee.giiis.unex.es.thenoworder.ui.main.ProductViewModelFactory;

public class InjectorUtils {

    public static AppRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return AppRepository.getInstance(context,database.orderDao(),database.productDao(), executors);
    }

    public static HomeViewModelFactory provideHomeViewModelFactory (Context context){
        AppRepository r = provideRepository(context);
        return new HomeViewModelFactory(r);
    }

    public static GalleryViewModelFactory provideGalleryViewModelFactory (Context context){
        AppRepository r = provideRepository(context);
        return new GalleryViewModelFactory(r);
    }

    public static SelectTableViewModelFactory provideSelectTableViewModelFactory (Context context){
        AppRepository r = provideRepository(context);
        return new SelectTableViewModelFactory(context,r);
    }

    public static SummaryOrderViewModelFactory provideSummartOrderViewModelFactory (Context context, long order_id){
        AppRepository r = provideRepository(context);
        return new SummaryOrderViewModelFactory(r,order_id);
    }

    public static CreateOrderViewModelFactory provideCreateOrderViewModelFactory (Context context, Order order){
        AppRepository r = provideRepository(context);
        return new CreateOrderViewModelFactory(r,order);
    }

    public static ProductViewModelFactory provideProductViewModelFactory (Context context){
        AppRepository r = provideRepository(context);
        return new ProductViewModelFactory(r);
    }
}
