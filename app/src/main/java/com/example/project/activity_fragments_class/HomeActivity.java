package com.example.project.activity_fragments_class;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.activity_fragments_class.Fragments.HomeFragment;
import com.example.project.activity_fragments_class.Fragments.HistoryFragment;
import com.example.project.activity_fragments_class.Fragments.TestFragment;
import com.example.project.activity_fragments_class.Fragments.PartnersFragment;
import com.example.project.activity_fragments_class.Fragments.CardsFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    public SharedPreferences myPrefs;
    public NavigationView navigationView;
    TextView cardNumber, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myPrefs = getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        cardNumber = view.findViewById(R.id.textView_leftNavigation_cardNumber);
        name = view.findViewById(R.id.textView_leftNavigation_name);
        cardNumber.setText("Numer karty: " + myPrefs.getString("login", ""));
        name.setText(myPrefs.getString("name", ""));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        /*showBadge(this, bottomNavigationView, R.id.nav_partners, "1999");*/


        Log.v("test", myPrefs.getString("login", ""));



        /*cardNumber.setText("Numer karty: " + myPrefs.getString("cardNumber", ""));
        name.setText(myPrefs.getString("login", ""));*/


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener;

    {
        navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case (R.id.nav_home):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.white)));
                        selectedFragment = new HomeFragment();
                        break;
                    case (R.id.nav_history):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        selectedFragment = new HistoryFragment();
                        break;
                    case (R.id.nav_partners):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        selectedFragment = new TestFragment();
                        break;
                    case (R.id.nav_presents):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        selectedFragment = new PartnersFragment();
                        break;
                    case (R.id.nav_test):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        selectedFragment = new CardsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            Log.v("App", "nav_camera");
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_rules) {
            Log.v("App", "nav_gallery");
        } else if (id == R.id.nav_logout) {
            Log.v("App", "nav_slideshow");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
