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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PayBillActivity extends AppCompatActivity {

    private TextView total_price, bill_message;
    private EditText cash;
    private Button pay_bill;
    private RecyclerView bill_product_list;
    private Order order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);

        getIntentOrder();
        init();

        this.total_price = (TextView) findViewById(R.id.total_price_bill_tv);
        this.total_price.setText(String.valueOf(order.getTotal_price()));

        this.bill_message = (TextView) findViewById(R.id.msg_bill_tv);

        this.cash= (EditText) findViewById(R.id.introduce_cash_et);

        this.pay_bill =(Button) findViewById(R.id.pay_bill_button);

        this.bill_product_list = (RecyclerView) findViewById(R.id.rv_record_product_list);
        SummaryProductAdapter SP_adapter = new SummaryProductAdapter(this,this.order.getProduct_list(), this.order,false);
        LinearLayoutManager LLManager = new LinearLayoutManager(this);
        LLManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.bill_product_list.setLayoutManager(LLManager);
        this.bill_product_list.setAdapter(SP_adapter);

        this.cash.addTextChangedListener(ShowTextAndButton);

        manageButton();


    }

    private void getIntentOrder(){
        Intent intent = getIntent();
        this.order = (Order) intent.getSerializableExtra(getString(R.string.intentOrder));
    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getResources().getString(R.string.pay_bill_activity));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private TextWatcher ShowTextAndButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (cash.getText().toString().length() > 0){

                Float cashInput = Float.parseFloat(cash.getText().toString().trim());
                Float resultado = cashInput - order.getTotal_price();

                if (resultado >= 0) {
                    bill_message.setText("El cambio es de " + resultado);
                    pay_bill.setEnabled(true);
                } else {
                    bill_message.setText("La catidad no es suficiente");
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
        this.pay_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setPaid_order(true);
                new updateOrder().execute(order);
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

    /*2. Update order*/
    class updateOrder extends AsyncTask<Order, Void, Long> {

        @Override
        protected Long doInBackground(Order... orders) {
            AppDatabase database = AppDatabase.getDatabase(PayBillActivity.this);

            long id = database.orderDao().updateOrder(order);
            return id;
        }
    }


}
