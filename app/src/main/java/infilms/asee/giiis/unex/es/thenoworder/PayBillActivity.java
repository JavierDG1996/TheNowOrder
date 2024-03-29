package infilms.asee.giiis.unex.es.thenoworder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import infilms.asee.giiis.unex.es.thenoworder.adapters.SummaryProductAdapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder.SummaryOrderViewModel;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder.SummaryOrderViewModelFactory;
import infilms.asee.giiis.unex.es.thenoworder.utilities.InjectorUtils;

public class PayBillActivity extends AppCompatActivity {

    private TextView total_price, bill_message;
    private EditText cash;
    private Button pay_bill;
    private RecyclerView bill_product_list;
    private Order order;
    private long id_order;

    private SummaryOrderViewModel summaryOrderViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);

        getIntentOrder();
        init();

        this.total_price = findViewById(R.id.total_price_bill_tv);


        this.bill_message = findViewById(R.id.msg_bill_tv);

        this.cash= findViewById(R.id.introduce_cash_et);

        this.pay_bill = findViewById(R.id.pay_bill_button);

        this.bill_product_list = findViewById(R.id.rv_record_product_list);

        SummaryOrderViewModelFactory factory = InjectorUtils.provideSummartOrderViewModelFactory(this,id_order);
        this.summaryOrderViewModel = ViewModelProviders.of(this,factory).get(SummaryOrderViewModel.class);

        this.summaryOrderViewModel.getOrder().observe(this,savedOrder->{
            this.order = savedOrder;

            this.total_price.setText(String.valueOf(order.getTotal_price()));

            SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this,this.order.getProduct_list(), this.order,false);
            LinearLayoutManager LLManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

            this.bill_product_list.setLayoutManager(LLManager);
            /*
             * Use this setting to improve performance if you know that changes in content do not
             * change the child layout size in the RecyclerView
             */
            this.bill_product_list.setHasFixedSize(true);
            this.bill_product_list.setAdapter(SP_adapter);
        });

        this.cash.addTextChangedListener(ShowTextAndButton);

        manageButton();


    }

    private void getIntentOrder(){
        Intent intent = getIntent();
        this.id_order = intent.getLongExtra(getString(R.string.intentOrder),0);
    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.pay_bill_activity));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private TextWatcher ShowTextAndButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (cash.getText().toString().length() > 0){

                float cashInput = Float.parseFloat(cash.getText().toString().trim());
                float resultado = cashInput - order.getTotal_price();

                BigDecimal bd = new BigDecimal(resultado);//Truncamos el precio total a dos decimales
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                resultado = bd.floatValue();

                if (resultado >= 0) {
                    bill_message.setText("El cambio es de " + resultado);
                    pay_bill.setEnabled(true);
                } else {
                    bill_message.setText("La cantidad no es suficiente");
                    pay_bill.setEnabled(false);
                }
            }else{
                bill_message.setText("Coloque cantidad en efectivo");
                pay_bill.setEnabled(false);

            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void manageButton(){
        this.pay_bill.setOnClickListener(view -> {
            order.setPaid_order(true);
            summaryOrderViewModel.updateOrder();
            finish();
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
