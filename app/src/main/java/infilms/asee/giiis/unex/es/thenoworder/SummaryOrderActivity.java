package infilms.asee.giiis.unex.es.thenoworder;

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
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class SummaryOrderActivity extends AppCompatActivity {

    private Order order;
    private RecyclerView order_products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_order);


        init();
        getIntents();
        loadView();

    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.create_order));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void getIntents(){
        //intent captation order
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(getString(R.string.intentOrder));

    }

    public void loadView(){
        this.order_products = findViewById(R.id.order_summary_products);
        SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this,this.order.getProduct_list());
        LinearLayoutManager LLManager = new LinearLayoutManager(this);
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.order_products.setLayoutManager(LLManager);
        this.order_products.setAdapter(SP_adapter);
    }


    private void saveOrder(){
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
            saveOrder();
            finishAffinity(); //Este método finaliza la actividad, así como todas las actividades debajo de ella en la tarea actual que tengan la misma afinidad.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

}
