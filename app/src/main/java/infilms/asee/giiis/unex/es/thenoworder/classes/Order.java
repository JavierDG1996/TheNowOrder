package infilms.asee.giiis.unex.es.thenoworder.classes;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import infilms.asee.giiis.unex.es.thenoworder.roomDatabase.Converters;

@Entity(tableName = "Order")
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id_order;
    private int table;
    private float total_price;
    @TypeConverters({Converters.class})
    private ArrayList<Product> product_list;

    public Order(int table, ArrayList<Product> product_list){

        /* TODO: delete asignation to identifier*/
        this.id_order = 0;
        this.table = table;
        this.product_list = product_list;

        this.total_price = calculateTotalPrice();
    }

    private float calculateTotalPrice() {
        this.total_price = 0;

        for(Product product : this.product_list){
            this.total_price = this.total_price + product.getProduct_price();
        }
        return this.total_price;
    }

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public ArrayList<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(ArrayList<Product> product_list) {
        this.product_list = product_list;
    }


}
