package infilms.asee.giiis.unex.es.thenoworder.classes;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id_product;
    private String product_name;
    private float product_price;
    private String product_type;

    public Product(String product_name, float product_price, String product_type) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_type = product_type;
    }

    public long getId_product() {
        return id_product;
    }

    public void setId_product(long id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
