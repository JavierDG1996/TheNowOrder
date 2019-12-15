package infilms.asee.giiis.unex.es.thenoworder.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

@Database(entities = {Order.class, Product.class}, version = 3, exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase {

    private static AppDatabase instance;

    private static final Object LOCK = new Object();

    public static AppDatabase getDatabase(Context context){
        if (instance == null){
            //fallbackToDestructiveMigration -> Hace que los datos antiguos de la aplicación se eliminen, ya que
            // no encajan en el nuevo modelo de la BD (no tienen el atributo y tal)
            // Si alguna vez no quieres que se borren los datos que tienes al hacer cambios en la BD, tendrás que
            // indicar cómo llevar a cabo la migración de la información
                synchronized (LOCK) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"app.db").fallbackToDestructiveMigration().build();
                }
            }

        return instance;
    }

    public abstract OrderDao orderDao();

    public abstract ProductDao productDao();
}
