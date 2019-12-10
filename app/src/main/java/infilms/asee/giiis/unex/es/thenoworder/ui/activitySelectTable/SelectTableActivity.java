package infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import infilms.asee.giiis.unex.es.thenoworder.R;
import infilms.asee.giiis.unex.es.thenoworder.adapters.Table_List_Adapter;
import infilms.asee.giiis.unex.es.thenoworder.classes.Table;
import infilms.asee.giiis.unex.es.thenoworder.utilities.InjectorUtils;


import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;


public class SelectTableActivity extends AppCompatActivity {

    private List<Table> lstTable;
    private SelectTableViewModel selectTableViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);

        init();

        lstTable = new ArrayList<>();
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //String mesas = sharedPreferences.getString(SettingsFragment.KEY_PREF_MESA,"0");

        SelectTableViewModelFactory factory = InjectorUtils.provideSelectTableViewModelFactory(this);
        this.selectTableViewModel = ViewModelProviders.of(this,factory).get(SelectTableViewModel.class);

        selectTableViewModel.getTables().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                for (int i =1; i <= integer/*Integer.parseInt(mesas)*/; i++){
                    lstTable.add(new Table("Mesa "+i,i,R.drawable.dining_table));
                }
            }
        });





        RecyclerView rv_table_list = (RecyclerView) findViewById(R.id.table_list_id);
        Table_List_Adapter tableAdapter = new Table_List_Adapter(this,lstTable);
        rv_table_list.setLayoutManager(new GridLayoutManager(this,3));
        rv_table_list.setAdapter(tableAdapter);

    }

    public void init(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getResources().getString(R.string.select_table_string));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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
