package com.example.project.activity_fragments_class.Registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.project.R;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Parser;
import com.example.project.Utils.Regex_patterns;
import com.example.project.activity_fragments_class.CreatePasswordActivity;
import com.example.project.activity_fragments_class.LoginActivity;
import com.example.project.activity_fragments_class.StartActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Document;



public class SignupActivity_FirstStep extends AppCompatActivity {
    private Button  buttonNext;
    private EditText editTextCardCode, editTextCardNumber;
    private SharedPreferences myPrefsRegister;
    private Toolbar toolbar;
    private RelativeLayout transLayout;
    private TextView text_dummy_hint_cardNumber, text_dummy_hint_cardCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_first_step);

        text_dummy_hint_cardCode = findViewById(R.id.textView_activitySignupFirstStep_dummyHintCardCode);
        text_dummy_hint_cardNumber = findViewById(R.id.textView_activitySignupFirstStep_dummyHintCardNumber);

        editTextCardCode = findViewById(R.id.editText_activitySignupFirstStep_cardCode);
        editTextCardNumber = findViewById(R.id.editText_activitySignupFirstStep_cardNumber);

        buttonNext = findViewById(R.id.button_activitySignupFirstStep_next);
        toolbar = findViewById(R.id.toolbar_activitySignupFirstStep);
        transLayout = findViewById(R.id.layout_activitySignupFirstStep_transiston_create);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editTextCardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_cardNumber.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (editTextCardNumber.getText().length() > 0)
                        text_dummy_hint_cardNumber.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_cardNumber.setVisibility(View.INVISIBLE);
                }
            }
        });

        editTextCardCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_cardCode.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (editTextCardCode.getText().length() > 0)
                        text_dummy_hint_cardCode.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_cardCode.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regex_patterns regex_patterns = new Regex_patterns();
                if (regex_patterns.isValidCardNumber(editTextCardNumber.getText())) {
                    if(regex_patterns.isValidCardCode(editTextCardCode.getText())){
                        new sprawdzanieKarty(StartActivity.checkingCard_fID, editTextCardNumber.getText().toString(), editTextCardCode.getText().toString()).execute();
                    }else{
                        editTextCardCode.setError("Niepoporawny format kodu karty. Kod karty powinien składac się z 4 cyfr.");
                    }
                } else{
                    editTextCardNumber.setError("Niepoporawny format numeru karty. Numer karty powinien składac się z 6-7 cyfr.");
                }
            }
        });

    }




    public class sprawdzanieKarty extends AsyncTask<String, String, String>{

        Connection_API C_api = new Connection_API(SignupActivity_FirstStep.this);

        private String p_fID;
        private String p_uID;
        private String p_kod;

        sprawdzanieKarty(String fID, String uID, String kod ){
            this.p_fID = fID;
            this.p_uID = uID;
            this.p_kod = kod;
        }

        @Override
        protected String doInBackground(String... strings) {
           return C_api.checkingValidCard_API(p_fID, p_uID, p_kod);
        }
        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            String xs = par.parserXML(doc, "xs");
            Log.v("App", "xs = " + xs);
            String[] xd = par.parserXMLArray(doc, "xd", "elem");
            Log.v("App", "xd = " + xd[0]);

            if("0".equals(xs)) {
                if("3".equals(xd[0])){
                    new AlertDialog.Builder(SignupActivity_FirstStep.this)
                            .setTitle("Uwaga!")
                            .setMessage("Karta jest już zarejestrowana lub przypisana do innego konta")
                            .show();
                } else if("2".equals(xd[0])){
                    new AlertDialog.Builder(SignupActivity_FirstStep.this)
                            .setTitle("Uwaga!")
                            .setMessage("Klient jest już zarejestrowany ale jeszcze nie potwierdził swoich danych. Czy chcesz ponownie wysłać emaila aktywacyjnego?")
                            .setPositiveButton("Wyślij ponownie", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(SignupActivity_FirstStep.this, "Wysłano ponownie wiadomosc emailem", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                } else if("0".equals(xd[0])){
                    new AlertDialog.Builder(SignupActivity_FirstStep.this)
                            .setTitle("Uwaga!")
                            .setMessage("Podana karta nie istnieje w bazie danych")
                            .show();
                }
            } else if("1".equals(xs)){
                myPrefsRegister = getSharedPreferences(StartActivity.SharedP_REGISTER, MODE_PRIVATE);
                SharedPreferences.Editor edit = myPrefsRegister.edit();
                edit.putString("numerKarty", editTextCardNumber.getText().toString());
                edit.commit();


                transLayout = findViewById(R.id.layout_activitySignupFirstStep_transiston_create);
                Intent intent = new Intent(SignupActivity_FirstStep.this, SignupActivity_SecondStep.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SignupActivity_FirstStep.this, transLayout, ViewCompat.getTransitionName(transLayout));
                startActivity(intent, options.toBundle());
            }
        }

    }

}


