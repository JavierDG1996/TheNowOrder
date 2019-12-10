package infilms.asee.giiis.unex.es.thenoworder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.ViewModel.SummaryOrderViewModel;
import infilms.asee.giiis.unex.es.thenoworder.ViewModel.SummaryOrderViewModelFactory;
import infilms.asee.giiis.unex.es.thenoworder.adapters.SummaryProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.repository.repositoryPtt;
import infilms.asee.giiis.unex.es.thenoworder.utilities.InjectorUtils;


import android.content.Intent;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecordDetailsActivity extends AppCompatActivity {

    private TextView total_price, table, id;
    private Button delete_record;
    private RecyclerView record_product_list;
    private Order order;
    private long id_order;

    private SummaryOrderViewModel summaryOrderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);


        getIntentOrder();
        init();



        this.total_price = (TextView) findViewById(R.id.record_total_price_tv);


        this.table = (TextView) findViewById(R.id.record_table_tv);


        this.id = (TextView) findViewById(R.id.record_id_tv);



        this.delete_record =(Button) findViewById(R.id.delete_record_button);

        this.record_product_list = (RecyclerView) findViewById(R.id.rv_record_product_list);


        SummaryOrderViewModelFactory factory = InjectorUtils.provideSummartOrderViewModelFactory(this,id_order);
        this.summaryOrderViewModel = ViewModelProviders.of(this,factory).get(SummaryOrderViewModel.class);

        this.summaryOrderViewModel.getOrder().observe(this,savedOrder->{

            this.order = savedOrder;
            if(this.order != null) {
                this.total_price.setText(String.valueOf(order.getTotal_price()));
                this.table.setText(String.valueOf(order.getTable()));
                this.id.setText(String.valueOf(order.getId_order()));
                Toast.makeText(this, "Id: " + order.getId_order(), Toast.LENGTH_SHORT).show();


                SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this, this.order.getProduct_list(), this.order, false);
                LinearLayoutManager LLManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

                this.record_product_list.setLayoutManager(LLManager);
                /*
                 * Use this setting to improve performance if you know that changes in content do not
                 * change the child layout size in the RecyclerView
                 */
                this.record_product_list.setHasFixedSize(true);
                this.record_product_list.setAdapter(SP_adapter);
            }
        });

        manageButton();

    }

    private void getIntentOrder(){
        Intent intent = getIntent();
        this.id_order = intent.getLongExtra(getString(R.string.intentOrder),0);
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
                summaryOrderViewModel.deleteOrder();
                finish();
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


}
