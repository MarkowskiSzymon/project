package com.example.project.activity_fragments_class;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.project.R;
import com.example.project.Utils.listaPartnerow;
import com.matthewtamlin.android_utilities_library.helpers.StatusBarHelper;

public class StartActivity extends AppCompatActivity {

    public static String checkingOwnedCards_fID = "99";
    public static String login_fID = "99";
    public static String checkingTransactions_fID = "100";
    public static String accountRegistration_fID = "101";
    public static String checkingCard_fID = "102";
    public static String checkingEmialAndPhone_fID = "103";
    public static String checkingPartners_fID = "104";
    public static String scr_url = "https://api.delly.pl/Dkli_0_1_0.php";
    public static String cards_layout_url = "http://delly.pl/layout/images/karty/";
    public static String partners_layout_url = "http://delly.pl/layout/images/partnerzy/";
    public static String lostInternet = "Cannot connect with internet.";
    public static String SharedP_LOGIN = "myPrefsLogin";
    public static String SharedP_REGISTER = "myPrefsRegister";
    public static int MAX_TRY_COUNT = 3;
    public static int RETRY_BACKOFF_DELAY = 3000;
    public static String dellyRulesUrl = "https://delly.pl/docs/regulamin.pdf";
    public static String longitude = "50.056675";
    public static String latitude = "19.947416";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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
