package com.example.project.activity_fragments_class.Registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.project.R;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Parser;
import com.example.project.Utils.Regex_patterns;
import com.example.project.activity_fragments_class.StartActivity;

import org.w3c.dom.Document;

public class SignupActivity_ThirdStep extends AppCompatActivity {

    private RadioButton radioButtonMale, radioButtonFemale;
    private RadioGroup radioGroup;
    private SharedPreferences myPrefsRegister;
    private Button zarejestrujButton;
    private EditText wpisaneImie, wpisanyKodPocztowy;
    private ImageView wybierzDate;
    private CheckBox akceptacjaRegulaminu;
    private TextView dataUrodzenia, kodPocztowy, imie;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String sexStatus = null;
    private String wybranaDataUrodzenia = null;

    public void zmienneReferencyjne(){
        radioButtonMale = findViewById(R.id.RadioButtonMale);
        radioButtonFemale = findViewById(R.id.RadioButtonFemale);
        dataUrodzenia = findViewById(R.id.textViewWybranaDataUrodzenia);
        imie = findViewById(R.id.textViewImie);
        kodPocztowy = findViewById(R.id.textViewKodPocztowy);
        wpisaneImie = findViewById(R.id.editTextImie);
        wpisanyKodPocztowy = findViewById(R.id.editTextKodPocztowy);
        wybierzDate = findViewById(R.id.kalendarz);
        radioGroup = findViewById(R.id.radioGroup);
        akceptacjaRegulaminu = findViewById(R.id.checkBoxRegulamin);
        zarejestrujButton = findViewById(R.id.buttonRegister);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_dane_osobowe);
        zmienneReferencyjne();

        wybierzDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(SignupActivity_ThirdStep.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("App", "onDateSet: mm/dd/yyy: " + year + "-" + month + "-" + day);
                wybranaDataUrodzenia = year + "-" + month + "-" + day;
                dataUrodzenia.setText(wybranaDataUrodzenia);

            }
        };

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButtonFemale.isChecked()) {
                    sexStatus = "F";
                } else if (radioButtonMale.isChecked()) {
                    sexStatus = "M";
                }
            }
        });

        zarejestrujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regex_patterns regex_patterns = new Regex_patterns();
                if(regex_patterns.isValidName(wpisaneImie.getText())){
                    if("F".equals(sexStatus) || "M".equals(sexStatus)){
                        if(akceptacjaRegulaminu.isChecked()){
                                if(regex_patterns.isValidZipCode(wpisanyKodPocztowy.getText())){
                                    myPrefsRegister = getSharedPreferences(StartActivity.SharedP_REGISTER, MODE_PRIVATE);
                                    new rejestracjaUzytkownika(
                                            StartActivity.accountRegistration_fID,
                                            myPrefsRegister.getString("numerKarty", ""),
                                            sexStatus,
                                            wybranaDataUrodzenia,
                                            kodPocztowy.getText().toString().substring(0,2) + "-" + kodPocztowy.getText().toString().substring(2,5),
                                            myPrefsRegister.getString("nrTel", ""),
                                            myPrefsRegister.getString("email", ""),
                                            wpisaneImie.getText().toString()
                                    ).execute();
                                    startActivity(new Intent(SignupActivity_ThirdStep.this, SignupActivity_FourthStep.class));
                                }else{
                                    wpisanyKodPocztowy.setError("Wpisany kod pocztowy jest zjebany");
                            }
                        }else{
                            Toast.makeText(SignupActivity_ThirdStep.this, "Regulamin musi zostac zaakceptowany", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity_ThirdStep.this, "Musisz wybrac plec, mordo", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    wpisaneImie.setError("Podane imie jest zle");
                }
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
