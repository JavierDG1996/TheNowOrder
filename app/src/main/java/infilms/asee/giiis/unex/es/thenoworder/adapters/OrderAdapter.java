package infilms.asee.giiis.unex.es.thenoworder.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.SummaryOrderActivity;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.order_row, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.order_price.setText(String.valueOf(this.orderList.get(position).getTotal_price()));
        holder.order_table.setText(String.valueOf(this.orderList.get(position).getTable()));

        //holder.order_description.removeAllViews();
        //holder.order_description.addView(holder.order_price);
        //holder.order_description.addView(holder.order_table);

        holder.pay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**Intent intent = new Intent(context, PayOrderActivity.class);
                intent.putExtra(context.getString(R.string.intentOrder), orderList.get(position));
                context.startActivity(intent);
                 **/
            }
        });

        holder.edit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SummaryOrderActivity.class);
                intent.putExtra(context.getString(R.string.intentOrder), orderList.get(position));
                intent.putExtra(context.getString(R.string.intentIsInsert), false);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.orderList.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{//Los adaptadores de Recycler view deben contener una clase que extienda de viewHolder
        //Creamos las variables
        TextView order_price;
        TextView order_table;
        LinearLayout order_description;
        Button edit_order;
        Button pay_order;


        //constructor de clase interna y vinculamos los atributos
        public MyViewHolder(View view){
            super(view);

            order_price = (TextView) view.findViewById(R.id.order_price);
            order_table = (TextView) view.findViewById(R.id.order_table);
            order_description = (LinearLayout) view.findViewById(R.id.LinearLayout_order_list_all_order_id);

            edit_order = view.findViewById(R.id.edit_order);
            pay_order = view.findViewById(R.id.pay_order);

        }
    }
}
