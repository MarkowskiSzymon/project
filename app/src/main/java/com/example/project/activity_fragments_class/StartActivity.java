package com.example.project.activity_fragments_class;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.project.R;
import com.example.project.Utils.listaPartnerow;

public class StartActivity extends AppCompatActivity {

    public static String accountRegistration_fID = "101";
    public static String checkingTransactions_fID = "100";
    public static String checkingOwnedCards_fID = "99";
    public static String login_fID = "99";
    public static String checkingPartners_fID = "104";
    public static String scr_url = "https://api.delly.pl/Dkli_0_1_0.php";
    public static String cards_layout_url = "http://delly.pl/layout/images/karty/";
    public static String partners_layout_url = "http://delly.pl/layout/images/partnerzy/";
    public static String lostInternet = "Cannot connect with internet.";
    public static String SharedP_LOGIN = "myPrefsLogin";
    public static String SharedP_REGISTER = "myPrefsRegister";
    public static int MAX_TRY_COUNT = 3;
    public static int RETRY_BACKOFF_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        listaPartnerow listaPartnerow = new listaPartnerow();
        listaPartnerow.dodajPartnera();



    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        }, 2000);
    }
}
