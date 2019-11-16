package infilms.asee.giiis.unex.es.thenoworder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

public class SummaryProductAdapter extends RecyclerView.Adapter<SummaryProductAdapter.MyViewHolder> {


        private Context mContext;
        private List<Product> product_list;



    public SummaryProductAdapter(Context mContext, List<Product> product_list) {
        this.mContext = mContext;
        this.product_list = product_list;

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


    }

        @Override
        public int getItemCount() {
        return product_list.size();
    }

        public static class  MyViewHolder extends RecyclerView.ViewHolder{//Los adaptadores de Recycler view deben contener una clase que extienda de viewHolder
            //Creamos las variables
            TextView product_name;
            TextView product_price;



            //constructor de clase interna y vinculamos los atributos
            public MyViewHolder(View view){
                super(view);

                product_name = (TextView) view.findViewById(R.id.product_name);
                product_price = (TextView) view.findViewById(R.id.product_price);

            }
        }


}
