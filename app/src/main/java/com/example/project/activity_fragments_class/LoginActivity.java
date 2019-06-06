package com.example.project.activity_fragments_class;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
    private EditText editTextUsername, editTextPassword;
    private String Tabid = null;
    private String pfID = "99";
    private Connection_INTERNET conn;
    private Password_hash hash;
    private SharedPreferences myPrefs;
    private TextView textViewGoToRegistration, textViewGoToRules;
    private Button button_AcitivityLogin_Login;
    public ArrayList<String> listaStringow2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conn = new Connection_INTERNET(getApplicationContext());
        hash = new Password_hash();
        Tabid = conn.getDeviceId();

        editTextUsername = findViewById(R.id.editText_ActivityLogin_LoginCardNumber);
        editTextPassword = findViewById(R.id.editText_ActivityLogin_LoginPassword);
        textViewGoToRegistration = findViewById(R.id.textView_ActivityLogin_GoToRegistration);
        textViewGoToRules = findViewById(R.id.textView_ActivityLogin_Rules);
        button_AcitivityLogin_Login = findViewById(R.id.button_AcitivityLogin_Login);

        button_AcitivityLogin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoubleClickBlock.preventTwoClick(view);

                if (conn.isInternetAvailable()) {
                    if (TextUtils.isEmpty(editTextUsername.getText())) {
                        editTextUsername.setError("Username is required!");
                    }else if(editTextUsername.getText().toString().contains(" ")){
                        editTextUsername.setError("Space is not allowed!");
                    }else if (TextUtils.isEmpty(editTextPassword.getText())) {
                        editTextPassword.setError("Password is required!");
                    }else if(editTextPassword.getText().toString().contains(" ")){
                        editTextPassword.setError("Space is not allowed");
                    }else{
                        new Logowanie(Tabid, pfID, editTextUsername.getText().toString(), hash.tryHash(editTextPassword.getText().toString())).execute();
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
                edit.putString("login", editTextUsername.getText().toString());
                edit.putString("password", hash.tryHash(editTextPassword.getText().toString()));
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