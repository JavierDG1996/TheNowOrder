package infilms.asee.giiis.unex.es.thenoworder.roomDatabase;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;

@Dao
public interface OrderDao {

    @Insert
    public long addOrder(Order order);

    @Update
    public int updateOrder(Order order);

    @Delete
    public int deleteOrder(Order order);

    @Query("SELECT * FROM `Order` WHERE id_order = :order_id")
    public Order getById(long order_id);

    @Query("SELECT * FROM `Order`")
    public List<Order> getAllOrders();

    @Query("SELECT * FROM `Order` WHERE paid_order = '0'")
    public List<Order> getAllPendentOrders();
}
