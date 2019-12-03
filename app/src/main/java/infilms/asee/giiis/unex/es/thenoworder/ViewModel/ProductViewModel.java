package infilms.asee.giiis.unex.es.thenoworder.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import infilms.asee.giiis.unex.es.thenoworder.classes.ProductList;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<ProductList> foodList;
    private MutableLiveData<ProductList> drinkList;
    private MutableLiveData<ProductList> dessertList;

    public ProductViewModel(){
        this.foodList =new MutableLiveData<>();
        this.drinkList =new MutableLiveData<>();
        this.dessertList =new MutableLiveData<>();
    }

    public MutableLiveData<ProductList> getFoodList(){
        return this.foodList;
    }

    public void setFoodList(ProductList productlist){
        this.foodList.postValue(productlist);
    }

    public MutableLiveData<ProductList> getDrinkList(){
        return this.drinkList;
    }

    public void setDrinkList(ProductList productlist){
        this.drinkList.postValue(productlist);
    }

    public MutableLiveData<ProductList> getDessertList(){
        return this.dessertList;
    }

    public void setDessertList(ProductList productlist){
        this.dessertList.postValue(productlist);
    }


}
