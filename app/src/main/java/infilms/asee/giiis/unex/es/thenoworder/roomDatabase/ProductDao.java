package infilms.asee.giiis.unex.es.thenoworder.roomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Product> products);


    @Query("SELECT * FROM `Product` WHERE product_type = 'Food'")
    LiveData<List<Product>> getFoodProducts();

    @Query("SELECT * FROM `Product` WHERE product_type = 'Drinks'")
    LiveData<List<Product>> getDrinkProducts();

    @Query("SELECT * FROM `Product` WHERE product_type = 'Dessert'")
    LiveData<List<Product>> getDessertProducts();

    @Query("DELETE FROM `Product` WHERE product_type = 'Food'")
    void deleteFoodProducts();

    @Query("DELETE FROM `Product` WHERE product_type = 'Drinks'")
    void deleteDrinkProducts();

    @Query("DELETE FROM `Product` WHERE product_type = 'Dessert'")
    void deleteDessertProducts();

}
