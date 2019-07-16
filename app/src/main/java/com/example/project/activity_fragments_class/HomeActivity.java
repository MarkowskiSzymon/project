package com.example.project.activity_fragments_class;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.activity_fragments_class.Fragments.CardsFragment;
import com.example.project.activity_fragments_class.Fragments.MainFragment;
import com.example.project.activity_fragments_class.Fragments.PartnersFragment;
import com.example.project.activity_fragments_class.Fragments.RewardsFragment;
import com.example.project.activity_fragments_class.Fragments.TransactionsFragment;
import com.example.project.model.LoginModelTest;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public SharedPreferences myPrefs;
    TextView cardNumber, name;
    public static int partnerFragmentStatus;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_full);
        intialize();
        setSupportActionBar(toolbar);
        LoginModelTest loginModelTest = new LoginModelTest();
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
        name.setText(loginModelTest.getImie());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();

    }

    private void intialize() {
        toolbar =  findViewById(R.id.toolbar_activityRules);
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
                        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
                        selectedFragment = new MainFragment();
                        break;
                    case (R.id.nav_history):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDellyGradientBlue));
                        selectedFragment = new TransactionsFragment();
                        break;
                    case (R.id.nav_partners):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDellyGradientBlue));
                        selectedFragment = new PartnersFragment();
                        break;
                    case (R.id.nav_presents):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDellyGradientBlue));
                        Log.v("tabs", "wyłączam nagrody");
                        selectedFragment = new MainActivity();
                        break;
                    case (R.id.nav_test):
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDellyGradientBlue)));
                        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDellyGradientBlue));
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

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            Log.v("App", "nav_settings");
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_rules) {
            Log.v("App", "nav_rules");
            Intent intent = new Intent(HomeActivity.this, RulesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.alert_light_frame)
                    .setTitle(R.string.logout)
                    .setMessage(R.string.logoutAsking)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
