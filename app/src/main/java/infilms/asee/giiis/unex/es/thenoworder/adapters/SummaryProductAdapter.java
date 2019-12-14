package infilms.asee.giiis.unex.es.thenoworder.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.classes.Order;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

public class SummaryProductAdapter extends RecyclerView.Adapter<SummaryProductAdapter.MyViewHolder> {


        private Context mContext;
        private List<Product> product_list;
        private Order order;//Este atributo es utilizado cuando se vaya a borrar un producto
        private boolean CanIInteract;


    public SummaryProductAdapter(Context mContext, List<Product> product_list, Order order, boolean CanIInteract) {
        this.mContext = mContext;
        this.product_list = product_list;
        this.order = order;
        this.CanIInteract = CanIInteract;
    }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.product_row, parent,false);

        return new MyViewHolder(view);
    }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.product_name.setText(product_list.get(position).getProduct_name());
        holder.product_price.setText(String.valueOf(product_list.get(position).getProduct_price()));

        if(CanIInteract) {
            holder.product_description.setOnClickListener(view -> showDeleteTakeDialog(product_list.get(position).getProduct_name(), position));
        }


    }

        @Override
        public int getItemCount() {
            if(null == this.product_list) return 0;
            return product_list.size();
    }

        static class  MyViewHolder extends RecyclerView.ViewHolder{//Los adaptadores de Recycler view deben contener una clase que extienda de viewHolder
            //Creamos las variables
            TextView product_name;
            TextView product_price;
            LinearLayout product_description;


            //constructor de clase interna y vinculamos los atributos
            MyViewHolder(View view){
                super(view);

                product_name = view.findViewById(R.id.product_name);
                product_price = view.findViewById(R.id.product_price);
                product_description = view.findViewById(R.id.LinearLayout_product_list_new_order_id);

            }
        }

        private void showDeleteTakeDialog(String productName, final int position){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext );
            String title = mContext.getString(R.string.delete_product_question) + " " + productName;

            alertDialog.setTitle(title).setPositiveButton(mContext.getString(R.string.delete), (dialogInterface, i) -> {
                product_list.remove(position);
                order.calculateTotalPrice();
                notifyDataSetChanged();
            }).setNegativeButton(mContext.getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.cancel()).show();

        }

}
