package infilms.asee.giiis.unex.es.thenoworder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.adapters.Table_List_Adapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Table;

import android.os.Bundle;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class SelectTableActivity extends AppCompatActivity {

    List<Table> lstTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);

        lstTable = new ArrayList<>();
        lstTable.add(new Table("Mesa 1",1,R.drawable.table));
        lstTable.add(new Table("Mesa 2",2,R.drawable.table));
        lstTable.add(new Table("Mesa 3",3,R.drawable.table));
        lstTable.add(new Table("Mesa 4",4,R.drawable.table));
        lstTable.add(new Table("Mesa 5",5,R.drawable.table));
        lstTable.add(new Table("Mesa 6",6,R.drawable.table));
        lstTable.add(new Table("Mesa 7",7,R.drawable.table));
        lstTable.add(new Table("Mesa 8",8,R.drawable.table));
        lstTable.add(new Table("Mesa 9",9,R.drawable.table));
        lstTable.add(new Table("Mesa 10",10,R.drawable.table));

        RecyclerView rv_table_list = (RecyclerView) findViewById(R.id.table_list_id);
        Table_List_Adapter tableAdapter = new Table_List_Adapter(this,lstTable);
        rv_table_list.setLayoutManager(new GridLayoutManager(this,3));
        rv_table_list.setAdapter(tableAdapter);

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
