package com.example.project.activity_fragments_class;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.DoubleClickBlock;
import com.example.project.Utils.Parser;
import com.example.project.R;
import com.example.project.Utils.Password_hash;
import com.example.project.activity_fragments_class.Registration.SignupActivity_FirstStep;
import com.example.project.model.LoginModel;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextCardNumber;
    private TextInputEditText editTextPassword;
    private Connection_INTERNET conn;
    private Password_hash hash;
    private SharedPreferences myPrefs;
    private TextView textViewGoToRegistration, textViewGoToRules, text_dummy_hint_cardNumber, text_dummy_hint_password;
    private Button button_AcitivityLogin_Login;
    private TextInputLayout textinputlayout_activitySettings_cardNumber, textinputlayout_activitySettings_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conn = new Connection_INTERNET(getApplicationContext());
        hash = new Password_hash();


        textinputlayout_activitySettings_cardNumber = findViewById(R.id.textinputlayout_activitySettings_cardNumber);
        textinputlayout_activitySettings_password = findViewById(R.id.textinputlayout_activitySettings_password);

        editTextCardNumber = findViewById(R.id.editText_activitySettings_cardNumber);
        editTextPassword = findViewById(R.id.editText_activitySettings_password);




        textViewGoToRegistration = findViewById(R.id.textView_ActivityLogin_GoToRegistration);
        textViewGoToRules = findViewById(R.id.textView_ActivityLogin_Rules);
        text_dummy_hint_cardNumber = findViewById(R.id.textView_activityLogin_dummyHintCardNumber);
        text_dummy_hint_password = findViewById(R.id.textView_activityLogin_dummyHintPassword);

        button_AcitivityLogin_Login = findViewById(R.id.button_AcitivityLogin_Login);



        button_AcitivityLogin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoubleClickBlock.preventTwoClick(view);

                if (conn.isInternetAvailable()) {
                    if (TextUtils.isEmpty(editTextCardNumber.getText())) {
                        editTextCardNumber.requestFocus();
                        textinputlayout_activitySettings_cardNumber.setError("Username is required!");
                    }else if(editTextCardNumber.getText().toString().contains(" ")){
                        textinputlayout_activitySettings_cardNumber.requestFocus();
                        textinputlayout_activitySettings_cardNumber.setError("Space is not allowed!");
                    }else if (TextUtils.isEmpty(editTextPassword.getText())) {
                        textinputlayout_activitySettings_password.requestFocus();
                        textinputlayout_activitySettings_password.setError("sdfsdfsdfsdf");
                    }else if(editTextPassword.getText().toString().contains(" ")){
                        editTextPassword.requestFocus();
                        textinputlayout_activitySettings_password.requestFocus();
                        editTextPassword.setError("Space is not allowed");
                    }else{
                        new Logowanie(conn.getDeviceId(), StartActivity.login_fID, editTextCardNumber.getText().toString(), hash.tryHash(editTextPassword.getText().toString())).execute();
                    }
                } else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Warning!")
                            .setMessage(StartActivity.lostInternet)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    System.exit(0);
                                }
                            })
                            .show();
                }
            }
        });

        textViewGoToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conn.isInternetAvailable()){
                    Intent intent  = new Intent(view.getContext(), SignupActivity_FirstStep.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });


        textViewGoToRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conn.isInternetAvailable()){
                    Intent intent  = new Intent(LoginActivity.this, RulesActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });



        editTextCardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            text_dummy_hint_cardNumber.setVisibility(View.VISIBLE);
                            textinputlayout_activitySettings_password.setError(null);

                        }
                    }, 100);
                } else {
                    if (editTextCardNumber.getText().length() > 0){
                        text_dummy_hint_cardNumber.setVisibility(View.VISIBLE);
                    } else{
                        text_dummy_hint_cardNumber.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_password.setVisibility(View.VISIBLE);
                            textinputlayout_activitySettings_cardNumber.setError(null);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (editTextPassword.getText().length() > 0)
                        text_dummy_hint_password.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_password.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    public class Logowanie extends AsyncTask<String, String, String> {

        Connection_API C_api = new Connection_API(LoginActivity.this);

        private String pTabid;
        private String pfID;
        private String pusername;
        private String ppassword;

        Logowanie(String Tabid, String fID, String username, String password) {
            this.pTabid = Tabid;
            this.pfID = fID;
            this.pusername = username;
            this.ppassword = password;
        }



        @Override
        protected String doInBackground(String... strings)  {
            return C_api.login_API(pfID, pTabid, pusername, ppassword);

        }

        @Override
        protected void onPostExecute(String result){
            Parser par = new Parser();
            Document doc = par.getDocument(result);
            myPrefs = getSharedPreferences(StartActivity.SharedP_LOGIN, MODE_PRIVATE);
            SharedPreferences.Editor edit = myPrefs.edit();

            String xs = par.parserXML(doc, "xs");

            par.parserLoginXML(doc, "xd");

            LoginModel listaStringow = new LoginModel();
            ArrayList<String> listOfValues = listaStringow.getListaStringow();

            if(xs.equals("1")){
                edit.putString("cardNumber", listOfValues.get(0));
                edit.putString("name", listOfValues.get(1));
                edit.putString("phoneNumber", listOfValues.get(2));
                edit.putString("email", listOfValues.get(3));
                edit.putString("zipCode", listOfValues.get(4));
                edit.putString("dateOfBirth", listOfValues.get(5));
                edit.putString("sex", listOfValues.get(6));
                edit.putString("creationDate", listOfValues.get(7));
                edit.putString("registerAccountLevel", listOfValues.get(8));
                edit.putString("niewiemcoto", listOfValues.get(9));
                edit.putString("login", editTextCardNumber.getText().toString());
                edit.putString("password", hash.tryHash(editTextPassword.getText().toString()));
                Log.v("parser", listOfValues.get(8));
                edit.commit();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }else{
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("Blędny login/hasło. ")
                        .setTitle("Warning!")
                        .show();
            }
        }
    }
}