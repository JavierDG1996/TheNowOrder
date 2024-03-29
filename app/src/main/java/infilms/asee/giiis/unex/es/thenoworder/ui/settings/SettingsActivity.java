package infilms.asee.giiis.unex.es.thenoworder.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import infilms.asee.giiis.unex.es.thenoworder.R;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();

        if(findViewById(R.id.fragment_settings)!=null){

            if(savedInstanceState!=null)
                return;

            getFragmentManager().beginTransaction().add(R.id.fragment_settings, new SettingsFragment()).commit();
        }
    }


    public void init(){
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.settings));

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
