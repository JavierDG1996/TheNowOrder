package infilms.asee.giiis.unex.es.thenoworder.roomDatabase;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;

@Database(entities = {Order.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"app.db").build();
        }

        return instance;
    }

    public abstract OrderDao orderDao();
}
