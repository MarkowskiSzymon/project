package com.example.project.activity_fragments_class.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
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



public class SignupActivity_FirstStep extends AppCompatActivity {
    private Button  buttonNext;
    private EditText editTextCardCode, editTextCardNumber;
    private SharedPreferences myPrefsRegister;
    private Toolbar toolbar;
    private RelativeLayout transLayout, cardNumberLayout, cardCodeLayout;
    private TextInputLayout cardNumberInputLayout, cardCodeInputLayout;
    private TextView text_dummy_hint_cardNumber, text_dummy_hint_cardCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_first_step);


        text_dummy_hint_cardCode = findViewById(R.id.textView_activitySignupFirstStep_dummyHintCardCode);
        text_dummy_hint_cardNumber = findViewById(R.id.textView_activitySignupFirstStep_dummyHintCardNumber);

        editTextCardCode = findViewById(R.id.editText_activitySignupFirstStep_cardCode);
        editTextCardNumber = findViewById(R.id.editText_activitySignupFirstStep_cardNumber);

        cardNumberLayout = findViewById(R.id.relativeLayout_activitySignupFirstStep_cardNumber);
        cardCodeLayout = findViewById(R.id.relativeLayout_activitySignupFirstStep_cardCode);
        transLayout = findViewById(R.id.layout_activitySignupFirstStep_transiston_create);

        cardNumberInputLayout = findViewById(R.id.textinputlayout_activitySignupFirstStep_cardNumber);
        cardCodeInputLayout = findViewById(R.id.textinputlayout_activitySignupFirstStep_cardCode);


        buttonNext = findViewById(R.id.button_activitySignupFirstStep_next);

        toolbar = findViewById(R.id.toolbar_activitySignupFirstStep);


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
                            cardCodeInputLayout.setError(null);
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
                            cardNumberInputLayout.setError(null);
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
                if (regex_patterns.isValidCardNumber(editTextCardNumber.getText()) || editTextCardNumber.getText().toString().contains(" ")) {
                    if(regex_patterns.isValidCardCode(editTextCardCode.getText())){
                        new sprawdzanieKarty(StartActivity.checkingCard_fID, editTextCardNumber.getText().toString(), editTextCardCode.getText().toString()).execute();
                    }else{
                        cardCodeInputLayout.setError("Niepoporawny format kodu karty. Kod karty powinien składac się z 4 cyfr.");
                    }
                } else {
                    cardNumberInputLayout.setError("Niepoprawny wprowadzony numer karty.");
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

            if("1".equals(xs)){
                myPrefsRegister = getSharedPreferences(StartActivity.SharedP_REGISTER, MODE_PRIVATE);
                SharedPreferences.Editor edit = myPrefsRegister.edit();
                edit.putString("cardNumber", editTextCardNumber.getText().toString());
                edit.commit();


                transLayout = findViewById(R.id.layout_activitySignupFirstStep_transiston_create);
                Intent intent = new Intent(SignupActivity_FirstStep.this, SignupActivity_SecondStep.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SignupActivity_FirstStep.this, transLayout, ViewCompat.getTransitionName(transLayout));
                startActivity(intent, options.toBundle());
            }
        }

    }

}


