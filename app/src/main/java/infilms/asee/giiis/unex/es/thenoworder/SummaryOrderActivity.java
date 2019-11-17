package infilms.asee.giiis.unex.es.thenoworder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.adapters.SummaryProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;
import infilms.asee.giiis.unex.es.thenoworder.roomDatabase.AppDatabase;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SummaryOrderActivity extends AppCompatActivity {

    private static final int ADD_PRODUCT_ORDER = 0;
    private Order order;
    private RecyclerView order_products;
    private FloatingActionButton add_new_product;

    private Boolean insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_order);

        getIntents();
        init();
        loadView();
        manageButtons();
    }

    private void manageButtons() {

        this.add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewOrderTabbedActivity.class);
                intent.putExtra(getString(R.string.intentProducts), order.getProduct_list());
                intent.putExtra(getString(R.string.intentIsInsert), false);
                startActivityForResult(intent, ADD_PRODUCT_ORDER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){
            case ADD_PRODUCT_ORDER:{
                if(resultCode == RESULT_OK){
                    order.getProduct_list().clear();
                    order.getProduct_list().addAll((ArrayList<Product>) data.getSerializableExtra(getString(R.string.intentProducts)));
                    order_products.getAdapter().notifyDataSetChanged();
                }
                break;
            }
        }
    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();
        if(this.insert){
            actionBar.setTitle(getResources().getString(R.string.create_order));
        }else{
            actionBar.setTitle(getResources().getString(R.string.update_order));
        }
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void getIntents(){
        //intent captation order
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(getString(R.string.intentOrder));
        this.insert = intent.getBooleanExtra(getString(R.string.intentIsInsert), true);
    }

    public void loadView(){
        this.order_products = findViewById(R.id.order_summary_products);
        add_new_product = this.findViewById(R.id.add_new_product);
        SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this,this.order.getProduct_list());
        LinearLayoutManager LLManager = new LinearLayoutManager(this);
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.order_products.setLayoutManager(LLManager);
        this.order_products.setAdapter(SP_adapter);

        if(this.insert){
            this.add_new_product.hide();
        }else{
            this.add_new_product.show();
        }
    }


    private void insertOrder(){
        new createOrder().execute(this.order);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        this.finish();
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
            if(this.insert){
                insertOrder();
            }else{
                updateOrder();
            }
            finishAffinity(); //Este método finaliza la actividad, así como todas las actividades debajo de ella en la tarea actual que tengan la misma afinidad.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateOrder() {
        try {
            this.order.updatePrice();
            Log.w("updating...", "-> "+new updateOrder().execute(this.order).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** ASYNC TASKS **/
    /*1. Create new order*/
    class createOrder extends AsyncTask<Order, Void, Long> {

        @Override
        protected Long doInBackground(Order... orders) {
            AppDatabase database = AppDatabase.getDatabase(SummaryOrderActivity.this);

            long id = database.orderDao().addOrder(order);
            return id;
        }
    }

    /*2. Update order*/
    class updateOrder extends AsyncTask<Order, Void, Long> {

        @Override
        protected Long doInBackground(Order... orders) {
            AppDatabase database = AppDatabase.getDatabase(SummaryOrderActivity.this);

            long id = database.orderDao().updateOrder(order);
            return id;
        }
    }

}
