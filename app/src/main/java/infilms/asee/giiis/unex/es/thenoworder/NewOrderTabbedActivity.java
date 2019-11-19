package infilms.asee.giiis.unex.es.thenoworder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

import infilms.asee.giiis.unex.es.thenoworder.API.NetworkingAndroidHttpClientJSON;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;
import infilms.asee.giiis.unex.es.thenoworder.ui.main.SectionsPagerAdapter;

public class NewOrderTabbedActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private List<Product> drinks;
    private List<Product> foods;
    private List<Product> desserts;

    private ArrayList<Product> product_order;
    private Order newOrder;
    private int table_number;

    private Boolean isNewOrder;
    NetworkingAndroidHttpClientJSON api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_tabbed);
        api= new NetworkingAndroidHttpClientJSON();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getIntentData();
        init();
        manageTabs();

    }

    public void getIntentData(){

        this.isNewOrder = getIntent().getBooleanExtra(getString(R.string.intentIsInsert), true);
        if(!this.isNewOrder){
            this.product_order = new ArrayList<>((ArrayList<Product>) getIntent().getSerializableExtra(getString(R.string.intentProducts)));
        }else{
            this.product_order = new ArrayList<>();
            table_number = getIntent().getIntExtra(getString(R.string.intentNumTable),0);
            Toast.makeText(this,"Recibido mesa "+table_number,Toast.LENGTH_SHORT).show();
        }
    }

    public void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(!this.isNewOrder)
            actionBar.setTitle(getResources().getString(R.string.update_order));
        else
            actionBar.setTitle(getResources().getString(R.string.create_order));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    //Método para añadir un producto a una petición
    public void addProductToOrder(Product product){

        this.product_order.add(product);
        Toast.makeText(this,getString(R.string.added)+" "+product.getProduct_name(),Toast.LENGTH_SHORT).show();
    }

    //Método para cargar los productos
    private void loadProducts() {

        this.drinks = new ArrayList<>();
        Product soda01 = new Product("Pepsi", (float) 2.6);
        Product soda02 = new Product("Kas", (float) 2.6);
        Product soda03 = new Product("Tónica Schweppes", (float) 2.6);
        Product soda04 = new Product("Lipton", (float) 2.6);

        this.drinks.add(soda01);
        this.drinks.add(soda02);
        this.drinks.add(soda03);
        this.drinks.add(soda04);

        foods=api.getProduct_list();
/*
        this.foods = new ArrayList<>();
        Product food01 = new Product("Menú 1: pasta", (float) 7.99);
        Product food02 = new Product("Menú 2: cocido", (float) 8.99);
        Product food03 = new Product("Menú 3: pizza", (float) 9.99);
        Product food04 = new Product("Menú 4: pizza familiar", (float) 11.99);
        this.foods.add(food01);
        this.foods.add(food02);
        this.foods.add(food03);
        this.foods.add(food04);
*/
        this.desserts = new ArrayList<>();
        Product dessert01 = new Product("Helado", (float) 7.99);
        Product dessert02 = new Product("Zumo", (float) 8.99);
        Product dessert03 = new Product("Fruta", (float) 9.99);
        Product dessert04 = new Product("Tarta", (float) 11.99);
        this.desserts.add(dessert01);
        this.desserts.add(dessert02);
        this.desserts.add(dessert03);
        this.desserts.add(dessert04);

    }

    public void manageTabs(){

        loadProducts();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),drinks,foods,desserts);

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed(){
        if(!this.isNewOrder){
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
        }
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_order) {
            if(this.isNewOrder){
                newOrder = new Order(table_number, product_order);
                Intent intent = new Intent(this, SummaryOrderActivity.class);
                intent.putExtra(getString(R.string.intentOrder), newOrder);
                startActivity(intent);
            }else{
                Intent returnIntent = new Intent();
                returnIntent.putExtra(getString(R.string.intentProducts),this.product_order);
                setResult(RESULT_OK, returnIntent);
                this.finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}