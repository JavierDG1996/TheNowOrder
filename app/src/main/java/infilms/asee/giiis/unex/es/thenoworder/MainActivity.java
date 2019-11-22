package infilms.asee.giiis.unex.es.thenoworder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import infilms.asee.giiis.unex.es.thenoworder.ui.home.HomeFragment;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        manageNavigationDrawer();
        manageFloatingButton();


    }





   /* private void loadDefaultFragment() {
        //Carga fragment por defecto
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.mainActivityContent, new HomeFragment()).commit();
    }*/

    public void manageNavigationDrawer(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){

                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(),"Home is selected", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_home);
                        break;
                    case R.id.nav_gallery:
                        Toast.makeText(getApplicationContext(),"Gallery is selected", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_gallery);
                        break;
                    case R.id.nav_slideshow:
                        Toast.makeText(getApplicationContext(),"Slideshow is selected", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_slideshow);
                        break;
                    case R.id.nav_tools:
                        Toast.makeText(getApplicationContext(),"Tools is selected", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_tools);
                        break;
                    case R.id.nav_share:
                        Toast.makeText(getApplicationContext(),"Share is selected", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_share);
                        break;
                    case R.id.nav_send:
                        Toast.makeText(getApplicationContext(),"Send is selected", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_send);
                        break;


                }

                menuItem.setChecked(true);
                drawer.closeDrawer(GravityCompat.START);
                //drawer.closeDrawers();


                return true;
            }
        });
    }

    public void manageFloatingButton(){
        FloatingActionButton new_order = findViewById(R.id.new_order);
        new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectTableActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
