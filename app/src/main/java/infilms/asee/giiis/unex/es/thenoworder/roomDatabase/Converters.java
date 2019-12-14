package infilms.asee.giiis.unex.es.thenoworder.roomDatabase;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

public class Converters {

    @TypeConverter
    public static ArrayList<Product> string2product(String string_product){
        Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
        return new Gson().fromJson(string_product, listType);
    }

    @TypeConverter
    public static String product2string(ArrayList<Product> products){
        Gson gson = new Gson();
        return gson.toJson(products);
    }
}
