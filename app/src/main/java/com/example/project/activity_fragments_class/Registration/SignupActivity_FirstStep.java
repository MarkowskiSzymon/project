package com.example.project.activity_fragments_class.Registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.*;

import com.example.project.R;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Parser;
import com.example.project.Utils.Regex_patterns;
import com.example.project.activity_fragments_class.StartActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Document;


public class SignupActivity_FirstStep extends AppCompatActivity {
    private Button  przyciskDalej;
    private EditText kodKarty, numerKarty;
    private SharedPreferences myPrefsRegister;
    private String fID = "102";

    public void zmienneReferencyjne(){
        kodKarty = findViewById(R.id.editText_ActivitySignupFirstStep_Code);
        numerKarty = findViewById(R.id.editText_ActivitySignupFirstStep_Number);
        przyciskDalej = findViewById(R.id.button_ActivitySignupFirstStep_Dalej);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_first_step);
        zmienneReferencyjne();


        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(1);
        przyciskDalej.startAnimation(anim);




        przyciskDalej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regex_patterns regex_patterns = new Regex_patterns();
                if (regex_patterns.isValidCardNumber(numerKarty.getText())) {
                    if(regex_patterns.isValidCardCode(kodKarty.getText())){
                        new sprawdzanieKarty(fID, numerKarty.getText().toString(), kodKarty.getText().toString()).execute();
                    }else{
                        kodKarty.setError("Niepoporawny format kodu karty. Kod karty powinien składac się z 4 cyfr.");
                    }
                } else{
                    numerKarty.setError("Niepoporawny format numeru karty. Numer karty powinien składac się z 6-7 cyfr.");
                }
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                Log.v("App", "Numer zeskanowanej karty: " + result.getContents());
                numerKarty = findViewById(R.id.editText_ActivitySignupFirstStep_Number);
                numerKarty.setText(result.getContents());
            }
        }
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
                edit.putString("numerKarty", numerKarty.getText().toString());
                edit.commit();
                startActivity(new Intent(SignupActivity_FirstStep.this, SignupActivity_SecondStep.class));
            }
        }

    }

}


