package infilms.asee.giiis.unex.es.thenoworder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import infilms.asee.giiis.unex.es.thenoworder.NewOrderTabbedActivity;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.classes.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;

    private List<Product> product_list;

    private FragmentActivity fragmentActivity;

    public ProductAdapter(Context mContext, List<Product> product_list, FragmentActivity fragmentActivity) {
        this.mContext = mContext;
        this.product_list = product_list;
        this.fragmentActivity = fragmentActivity;
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

        holder.product_description.setOnClickListener(view -> {
            NewOrderTabbedActivity activity = (NewOrderTabbedActivity) fragmentActivity;
            activity.addProductToOrder(product_list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if(product_list != null)
            return product_list.size();
        return 0;
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

    public void load(List<Product> newProduct_list){
        product_list.clear();
        product_list= newProduct_list;
        notifyDataSetChanged();
    }

}
