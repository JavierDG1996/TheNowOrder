package infilms.asee.giiis.unex.es.thenoworder.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.ui.activitySummaryOrder.SummaryOrderActivity;
import infilms.asee.giiis.unex.es.thenoworder.classes.Table;

public class Table_List_Adapter extends RecyclerView.Adapter<Table_List_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Table> table_list;

    //método constructor del adaptador
    public Table_List_Adapter(Context mContext, List<Table> table_list){
        this.mContext = mContext;
        this.table_list = table_list;
    }

    //Este método es el encargado de inflar el contenido de un nuevo item para la lista. Método inflar es el procedimiento que se realiza para hacer uso de un layout dentro de otro layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_table, parent,false);//Haremos uso del layout cardview_item_table dentro del layout que define la recyclerview

        return new MyViewHolder(view);
    }

    //Este método es el encargado de realizar las modificaciones del contenido para cada item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_table_title.setText(table_list.get(position).getTitle());
        holder.img_table.setImageResource(table_list.get(position).getThumbnail());

        //añadimos el click listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(mContext, NewOrderTabbedActivity.class);
                Intent intent = new Intent(mContext, SummaryOrderActivity.class);
                intent.putExtra(mContext.getString(R.string.intentNumTable), table_list.get(position).getNumber());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return table_list.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{//Los adaptadores de Recycler view deben contener una clase que extienda de viewHolder
        //Creamos las variables
        TextView tv_table_title;
        ImageView img_table;
        CardView cardView;

        //constructor de clase interna y vinculamos los atributos
        public MyViewHolder(View view){
            super(view);

            tv_table_title = view.findViewById(R.id.table_number_id);
            img_table = view.findViewById(R.id.table_img_id);
            cardView = view.findViewById(R.id.cardview_table_id);
        }
    }
}
