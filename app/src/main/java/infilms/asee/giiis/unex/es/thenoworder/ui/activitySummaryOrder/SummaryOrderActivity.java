package infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import infilms.asee.giiis.unex.es.thenoworder.MainActivity;
import infilms.asee.giiis.unex.es.thenoworder.NewOrderTabbedActivity;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.adapters.SummaryProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;
import infilms.asee.giiis.unex.es.thenoworder.utilities.InjectorUtils;

public class SummaryOrderActivity extends AppCompatActivity {

    private static final int ADD_PRODUCT_ORDER = 0;
    private long id_order;
    private Order order;
    private RecyclerView order_products;
    private FloatingActionButton add_new_product;
    private Boolean insert;

    private SummaryOrderViewModel summaryOrderViewModel;
    private CreateOrderViewModel createOrderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_order);

        getIntents();
        init();
        loadView();
        manageButtons();
    }

    public void getIntents(){
        //intent captation order
        Intent intent = getIntent();
        this.insert = intent.getBooleanExtra(getString(R.string.intentIsInsert), true);
        if(this.insert){
            ArrayList<Product> product_order = new ArrayList<>();
            int table_number= getIntent().getIntExtra(getString(R.string.intentNumTable),0);
            Toast.makeText(this,"Recibido mesa "+table_number,Toast.LENGTH_SHORT).show();
            order = new Order(table_number, product_order);
        }else{
            this.id_order = intent.getLongExtra(getString(R.string.intentOrder),0);
        }

    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();
        if(this.insert){
            assert actionBar != null;
            actionBar.setTitle(getResources().getString(R.string.create_order));
        }else{
            assert actionBar != null;
            actionBar.setTitle(getResources().getString(R.string.update_order));
        }
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void loadView(){
        this.order_products = findViewById(R.id.order_summary_products);
        add_new_product = this.findViewById(R.id.add_new_product);

        if(this.insert){
            CreateOrderViewModelFactory factory = InjectorUtils.provideCreateOrderViewModelFactory(this,order);
            this.createOrderViewModel = ViewModelProviders.of(this,factory).get(CreateOrderViewModel.class);
            //Si se está insertando el pedido utiliza el pedido que se ha creado en el momento
            SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this, this.order.getProduct_list(), this.order, true);
            LinearLayoutManager LLManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            this.order_products.setLayoutManager(LLManager);
            /*
             * Use this setting to improve performance if you know that changes in content do not
             * change the child layout size in the RecyclerView
             */
            this.order_products.setHasFixedSize(true);
            this.order_products.setAdapter(SP_adapter);
        }else{
            //Si se está actualizando el pedido se debe recuperar utilizando el identificador del pedido

            SummaryOrderViewModelFactory factory = InjectorUtils.provideSummartOrderViewModelFactory(this,id_order);
            this.summaryOrderViewModel = ViewModelProviders.of(this,factory).get(SummaryOrderViewModel.class);

            this.summaryOrderViewModel.getOrder().observe(this,newOrder->{
                this.order = newOrder;
                SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this, this.order.getProduct_list(), this.order, true);
                LinearLayoutManager LLManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
                this.order_products.setLayoutManager(LLManager);
                /*
                 * Use this setting to improve performance if you know that changes in content do not
                 * change the child layout size in the RecyclerView
                 */
                this.order_products.setHasFixedSize(true);
                this.order_products.setAdapter(SP_adapter);
            });
        }
    }

    private void manageButtons() {

        this.add_new_product.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NewOrderTabbedActivity.class);
            intent.putExtra(getString(R.string.intentProducts), order.getProduct_list());
            startActivityForResult(intent, ADD_PRODUCT_ORDER);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PRODUCT_ORDER) {
            if (resultCode == RESULT_OK) {
                order.getProduct_list().clear();
                assert data != null;
                order.getProduct_list().addAll((ArrayList<Product>) data.getSerializableExtra(getString(R.string.intentProducts)));
                Objects.requireNonNull(order_products.getAdapter()).notifyDataSetChanged();
            }
        }
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
            this.order.calculateTotalPrice();
            Toast.makeText(this,"Numero productos "+ Objects.requireNonNull(this.order_products.getAdapter()).getItemCount(),Toast.LENGTH_SHORT).show();
            if(this.insert){
                createOrderViewModel.addOrder();
                finishAffinity(); //Este método finaliza la actividad, así como todas las actividades debajo de ella en la tarea actual que tengan la misma afinidad.
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                summaryOrderViewModel.updateOrder();
                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
