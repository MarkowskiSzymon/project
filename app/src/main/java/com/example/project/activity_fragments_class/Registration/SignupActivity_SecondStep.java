package com.example.project.activity_fragments_class.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import com.example.project.R;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Parser;
import com.example.project.Utils.Regex_patterns;
import com.example.project.activity_fragments_class.StartActivity;
import org.w3c.dom.Document;

public class SignupActivity_SecondStep extends AppCompatActivity {

    private Button  buttonNext;
    private EditText editTextCardEmail, editTextCardPhone;
    private SharedPreferences myPrefsRegister;
    private Toolbar toolbar;
    private TextView text_dummy_hint_email, text_dummy_hint_phone;
    private RelativeLayout transLayout;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_second_step);
        initialize();




        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editTextCardEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_dummy_hint_email.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editTextCardEmail.getText().length() > 0){
                        text_dummy_hint_email.setVisibility(View.VISIBLE);
                    } else{
                        text_dummy_hint_email.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


        editTextCardPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_dummy_hint_phone.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editTextCardPhone.getText().length() > 0)
                        text_dummy_hint_phone.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_phone.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regex_patterns regex_patterns = new Regex_patterns();
                if (!regex_patterns.isValidEmail(editTextCardEmail.getText()) || editTextCardEmail.getText().toString().contains(" ")) {
                    Log.v("signup", "Niepoprawny email");
                } else {
                    if (!regex_patterns.isValidPhoneNumber(editTextCardPhone.getText()) || editTextCardPhone.getText().toString().contains(" ")) {
                        Log.v("signup", "Niepoprawny phone");
                    } else {
                        new checkingEmail(StartActivity.checkingEmialAndPhone_fID, editTextCardEmail.getText().toString()).execute();
                    }
                }
            }
        });
    }

    private void initialize() {
        text_dummy_hint_email = findViewById(R.id.textView_activitySignupThirdStep_dummyHintName);
        text_dummy_hint_phone = findViewById(R.id.textView_activitySignupThirdStep_dummyHintDateOfBirth);
        editTextCardEmail = findViewById(R.id.editText_activitySignupThirdStep_name);
        editTextCardPhone = findViewById(R.id.editText_activitySignupThirdStep_dateOfBirth);
        toolbar = findViewById(R.id.toolbar_activitySignupSecondStep);
        buttonNext = findViewById(R.id.button_activitySignupSecondStep_next);
        transLayout = findViewById(R.id.layout_activitySignupSecondStep_transiston_create);
    }

    public class checkingEmail extends AsyncTask<String, String, String> {
        Connection_API C_api = new Connection_API(SignupActivity_SecondStep.this);

        private String p_fID;
        private String p_credit;

        checkingEmail(String fid, String credit){
            this.p_fID = fid;
            this.p_credit = credit;
        }

        @Override
        protected String doInBackground(String... strings) {
            return C_api.checkingEmail_API(p_fID, p_credit);
        }

        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            String xsEmail = par.parserXML(doc, "xs");
            if("1".equals(xsEmail)){
                new sprawdzanieTelefonu(StartActivity.checkingEmialAndPhone_fID, editTextCardPhone.getText().toString()).execute();
            }else{
                Log.v("signup", "Podany email został już zarejestrowany");
            }
        }
    }


    public class sprawdzanieTelefonu extends AsyncTask<String, String, String> {
        Connection_API C_api = new Connection_API(SignupActivity_SecondStep.this);

        private String p_fID;
        private String p_credit;

        sprawdzanieTelefonu(String fid, String credit){
            this.p_fID = fid;
            this.p_credit = credit;
        }

        @Override
        protected String doInBackground(String... strings) {
            return C_api.checkingPhone_API(p_fID, p_credit);
        }

        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            String xsTel = par.parserXML(doc, "xs");
            Log.v("signup", "XS Telefonu: " + xsTel);
            if("1".equals(xsTel)){
                myPrefsRegister = getSharedPreferences(StartActivity.SharedP_REGISTER, MODE_PRIVATE);
                SharedPreferences.Editor edit = myPrefsRegister.edit();
                edit.putString("email", editTextCardEmail.getText().toString());
                edit.putString("phone", editTextCardPhone.getText().toString());
                edit.commit();
                initialize();
                Intent intent = new Intent(SignupActivity_SecondStep.this, SignupActivity_ThirdStep.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SignupActivity_SecondStep.this, transLayout, ViewCompat.getTransitionName(transLayout));
                startActivity(intent, options.toBundle());
            } else{
               Log.v("signup", "Podany numer telefonu został już zarejestrowany");
            }
        }
    }
}
