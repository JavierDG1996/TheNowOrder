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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.PayBillActivity;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.RecordDetailsActivity;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder.SummaryOrderActivity;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<Order> orderList;
    private Context context;
    private boolean IsPaidOrders;

    public OrderAdapter(List<Order> orderList, Context context, boolean IsPaidOrders) {
        this.orderList = orderList;
        this.context = context;
        this.IsPaidOrders = IsPaidOrders;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        if(!IsPaidOrders)
             view = mInflater.inflate(R.layout.order_row, parent,false);
        else
            view = mInflater.inflate(R.layout.paid_row, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.order_price.setText(String.valueOf(this.orderList.get(position).getTotal_price()));
        holder.order_table.setText(String.valueOf(this.orderList.get(position).getTable()));


        if(!IsPaidOrders) {
            holder.pay_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PayBillActivity.class);
                    intent.putExtra(context.getString(R.string.intentOrder), orderList.get(position).getId_order());
                    context.startActivity(intent);


                }
            });

            holder.edit_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SummaryOrderActivity.class);
                    intent.putExtra(context.getString(R.string.intentOrder), orderList.get(position).getId_order());
                    intent.putExtra(context.getString(R.string.intentIsInsert), false);

                    context.startActivity(intent);
                }
            });
        }else {
           holder.record_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RecordDetailsActivity.class);
                    intent.putExtra(context.getString(R.string.intentOrder), orderList.get(position).getId_order());

                    context.startActivity(intent);
                }
            });
        }

    }



    public void swapOrderList(final List<Order> newOrderList) {
        // If there was no forecast data, then recreate all of the list
        if (orderList == null) {
            orderList = newOrderList;
            notifyDataSetChanged();
        } else {
            /*
             * Otherwise we use DiffUtil to calculate the changes and update accordingly. This
             * shows the four methods you need to override to return a DiffUtil callback. The
             * old list is the current list stored in mForecast, where the new list is the new
             * values passed in from the observing the database.
             */

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return orderList.size();
                }

                @Override
                public int getNewListSize() {
                    return newOrderList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return orderList.get(oldItemPosition).getId_order() ==
                            newOrderList.get(newItemPosition).getId_order();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Order newOrder = newOrderList.get(newItemPosition);
                    Order oldOrder = orderList.get(oldItemPosition);
                    return newOrder.getId_order() == oldOrder.getId_order()
                            && newOrder.getTable() == oldOrder.getTable();
                }
            });
            orderList = newOrderList;
            notifyDataSetChanged();
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemCount() {
        if(null == this.orderList) return 0;
        return this.orderList.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{//Los adaptadores de Recycler view deben contener una clase que extienda de viewHolder
        //Creamos las variables
        TextView order_price;
        TextView order_table;
        LinearLayout order_description;
        Button edit_order;
        Button pay_order;
        Button record_order;


        //constructor de clase interna y vinculamos los atributos
        public MyViewHolder(View view){
            super(view);

            order_price = view.findViewById(R.id.order_price);
            order_table = view.findViewById(R.id.order_table);
            order_description = view.findViewById(R.id.LinearLayout_order_list_all_order_id);

            edit_order = view.findViewById(R.id.edit_order);
            pay_order = view.findViewById(R.id.pay_order);
            record_order = view.findViewById(R.id.record_order);

        }
    }


}
