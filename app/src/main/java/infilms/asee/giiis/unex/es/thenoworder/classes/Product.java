package infilms.asee.giiis.unex.es.thenoworder.classes;

import java.io.Serializable;

public class Product implements Serializable {

    private String product_name;
    private float product_price;

    public Product(String product_name, float product_price) {
        this.product_name = product_name;
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return product_name;
    }


    public float getProduct_price() {
        return product_price;
    }

}
