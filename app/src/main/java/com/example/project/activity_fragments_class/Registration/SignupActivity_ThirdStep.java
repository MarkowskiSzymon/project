package com.example.project.activity_fragments_class.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.R;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Parser;
import com.example.project.activity_fragments_class.StartActivity;

import org.w3c.dom.Document;

public class SignupActivity_ThirdStep extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private Button przelaczAnimacje;
    private SharedPreferences myPrefsRegister;
    private RelativeLayout transLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_third_step);

        toolbar = findViewById(R.id.toolbar_activitySignupThirdStep);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public class rejestracjaUzytkownika extends AsyncTask<String, String, String> {

        Connection_API C_api = new Connection_API(SignupActivity_ThirdStep.this);

        private String p_fID;
        private String p_uID;
        private String p_p1;
        private String p_p2;
        private String p_p3;
        private String p_p4;
        private String p_p5;
        private String p_p6;

        rejestracjaUzytkownika(String fID, String uID, String p1, String p2, String p3, String p4, String p5, String p6){
            this.p_fID = fID;
            this.p_uID = uID;
            this.p_p1 = p1;
            this.p_p2 = p2;
            this.p_p3 = p3;
            this.p_p4 = p4;
            this.p_p5 = p5;
            this.p_p6 = p6;
        }

        @Override
        protected String doInBackground(String... strings) {
            return C_api.registration_API(p_fID, p_uID, p_p1, p_p2, p_p3, p_p4, p_p5, p_p6);
        }

        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            String xsRegister = par.parserXML(doc, "xs");
            Log.v("App", "xs: " + xsRegister);
        }
    }
}
