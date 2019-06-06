package com.example.project.activity_fragments_class.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project.R;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Parser;
import com.example.project.Utils.Regex_patterns;
import com.example.project.activity_fragments_class.StartActivity;

import org.w3c.dom.Document;

public class SignupActivity_SecondStep extends AppCompatActivity {

    Button przyciskDalej, przyciskCofnij;
    EditText email,telefon;
    private SharedPreferences myPrefsRegister;
    private final String fid = "103";

    public void zmienneReferencyjne(){
        email = findViewById(R.id.editTextEmail);
        telefon = findViewById(R.id.editTextNumerTelefonu);
        przyciskDalej = findViewById(R.id.dalejButtonFromContacts);
        przyciskCofnij = findViewById(R.id.cofnijButtonFromContacts);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__sprawdzanie__haslo__email);

        zmienneReferencyjne();

        przyciskDalej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regex_patterns regex_patterns = new Regex_patterns();
                if(regex_patterns.isValidEmail(email.getText())){
                    if(regex_patterns.isValidPhoneNumber(telefon.getText())){
                        new checkingEmail(fid,email.getText().toString()).execute();
                    }else{
                        telefon.setError("Niewlasciwy format podanego numeru telefonu");
                    }
                }else{
                    email.setError("Niewlasciwy format podanego emaila");
                }
            }
        });

        przyciskCofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity_SecondStep.this, SignupActivity_FirstStep.class));
            }
        });
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
            Log.v("App", "XS EMAILA: " + xsEmail);
            if("1".equals(xsEmail)){
                new sprawdzanieTelefonu(fid, telefon.getText().toString()).execute();
            }else{
                email.setError("Podany email został już zarejestrowany");
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
            Log.v("App", "XS Telefonu: " + xsTel);
            if("1".equals(xsTel)){
                myPrefsRegister = getSharedPreferences(StartActivity.SharedP_REGISTER, MODE_PRIVATE);
                SharedPreferences.Editor edit = myPrefsRegister.edit();
                edit.putString("email", email.getText().toString());
                edit.putString("nrTel", telefon.getText().toString());
                edit.commit();
                startActivity(new Intent(SignupActivity_SecondStep.this, SignupActivity_ThirdStep.class));
            }else{
                telefon.setError("Podany numer telefonu został już zarejestrowany");
            }
        }

    }
}
