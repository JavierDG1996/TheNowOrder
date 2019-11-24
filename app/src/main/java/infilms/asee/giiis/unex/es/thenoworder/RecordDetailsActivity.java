package infilms.asee.giiis.unex.es.thenoworder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.adapters.SummaryProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.roomDatabase.AppDatabase;


import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordDetailsActivity extends AppCompatActivity {

    private TextView total_price, table;
    private Button delete_record;
    private RecyclerView record_product_list;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);


        getIntentOrder();
        init();


        this.total_price = (TextView) findViewById(R.id.record_total_price_tv);
        this.total_price.setText(String.valueOf(order.getTotal_price()));

        this.table = (TextView) findViewById(R.id.record_table_tv);
        this.table.setText(String.valueOf(order.getTable()));


        this.delete_record =(Button) findViewById(R.id.delete_record_button);

        this.record_product_list = (RecyclerView) findViewById(R.id.rv_record_product_list);
        SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this,this.order.getProduct_list(), this.order,false);
        LinearLayoutManager LLManager = new LinearLayoutManager(this);
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.record_product_list.setLayoutManager(LLManager);
        this.record_product_list.setAdapter(SP_adapter);

        manageButton();

    }

    private void getIntentOrder(){
        Intent intent = getIntent();
        this.order = (Order) intent.getSerializableExtra(getString(R.string.intentOrder));
    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getResources().getString(R.string.record_order_activity));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void manageButton(){
        this.delete_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setPaid_order(true);
                new deleteOrder().execute(order);
                //finish();
                finishAffinity(); //Este método finaliza la actividad, así como todas las actividades debajo de ella en la tarea actual que tengan la misma afinidad.
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
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

    /*3. Delete order*/
    class deleteOrder extends AsyncTask<Order, Void, Long> {

        @Override
        protected Long doInBackground(Order... orders) {
            AppDatabase database = AppDatabase.getDatabase(RecordDetailsActivity.this);

            long id = database.orderDao().deleteOrder(order);
            return id;
        }
    }
}
