package infilms.asee.giiis.unex.es.thenoworder.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import infilms.asee.giiis.unex.es.thenoworder.classes.Product;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;

public class ProductViewModel extends ViewModel {

    private final repositoryPtt mRepository;
    private LiveData<List<Product>> foodList;
    private LiveData<List<Product>> drinkList;
    private LiveData<List<Product>> dessertList;

    public ProductViewModel(repositoryPtt repository){
        this.mRepository = repository;
        this.foodList = repository.getFoodProductList();
        this.drinkList = repository.getDrinkProductList();
        this.dessertList = repository.getDessertProductList();
    }

    public LiveData<List<Product>> getFoodList(){
        return this.foodList;
    }

    public LiveData<List<Product>> getDrinkList(){
        return this.drinkList;
    }

    public LiveData<List<Product>> getDessertList(){
        return this.dessertList;
    }
}
