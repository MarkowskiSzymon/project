package com.example.project.activity_fragments_class.Registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.w3c.dom.Document;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class SignupActivity_ThirdStep extends AppCompatActivity {

    private Button buttonNext;
    private SharedPreferences myPrefsRegister;
    private RelativeLayout transLayout;
    private TextView text_dummy_hint_name, text_dummy_hint_dateOfBirth, text_dummy_hint_zipCode;
    private EditText editTextName, edittextZipCode, editTextDateOfBirth;
    private TextView textViewDateOfBirth;
    private Toolbar toolbar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Integer myAge;
    private RadioGroup genderRadioGroup;
    private CheckBox rulesCheckBox;
    private String gender;
    private RadioButton maleRadioButton, femaleRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_third_step);

        transLayout = findViewById(R.id.layout_activitySignupThirdStep_transiston_create);
        final Regex_patterns regex = new Regex_patterns();
        myPrefsRegister = getSharedPreferences(StartActivity.SharedP_REGISTER, MODE_PRIVATE);
        final SharedPreferences.Editor edit = myPrefsRegister.edit();

        text_dummy_hint_name = findViewById(R.id.textView_activitySignupThirdStep_dummyHintName);
        text_dummy_hint_dateOfBirth = findViewById(R.id.textView_activitySignupThirdStep_dummyHintDateOfBirth);
        text_dummy_hint_zipCode = findViewById(R.id.textView_activitySignupThirdStep_dummyHintZipCode);

        editTextName = findViewById(R.id.editText_activitySignupThirdStep_name);
        edittextZipCode = findViewById(R.id.editText_activitySignupThirdStep_zipCode);
        edittextZipCode.setTransformationMethod(null);

        textViewDateOfBirth = findViewById(R.id.editText_activitySignupThirdStep_dateOfBirth);

        buttonNext = findViewById(R.id.button_activitySignupThirdStep_next);

        toolbar = findViewById(R.id.toolbar_activitySignupThirdStep);

        genderRadioGroup = findViewById(R.id.radioGroup_activitySignupThirdStep);

        rulesCheckBox = findViewById(R.id.checkbox_activitySignupThirdStep);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            text_dummy_hint_name.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editTextName.getText().length() > 0) {
                        text_dummy_hint_name.setVisibility(View.VISIBLE);
                    } else {
                        text_dummy_hint_name.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


        textViewDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupActivity_ThirdStep.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                String wiek = getAge(year, month, day);
                Log.v("third", "wiek" + wiek);
                textViewDateOfBirth.setText(date);
            }
        };

        edittextZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            text_dummy_hint_zipCode.setVisibility(View.VISIBLE);
                            edittextZipCode.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    Log.v("third", "afterTEXTchanged");
                                    if (!s.toString().contains("-") && s.length() > 2) {
                                        s.insert(2, "-");
                                    }
                                }
                            });
                        }
                    }, 100);
                } else {
                    if (edittextZipCode.getText().length() > 0) {
                        text_dummy_hint_zipCode.setVisibility(View.VISIBLE);
                    } else {
                        text_dummy_hint_zipCode.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                maleRadioButton = findViewById(R.id.radioButton_activitySignupThirdStep_male);
                femaleRadioButton = findViewById(R.id.radioButton_activitySignupThirdStep_female);

                if(maleRadioButton.isChecked()){
                    gender = "M";
                }else{
                    gender = "F";
                }
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!regex.isValidName(editTextName.getText()) || editTextName.getText().toString().contains(" ")) {
                    Log.v("third", "Imie niepoprawne");
                } else {
                    if (!textViewDateOfBirth.getText().toString().isEmpty()) {
                        if (myAge > 16) {
                            Log.v("third", "wiek > 16, czyli ok");
                            if(regex.isValidZipCode(edittextZipCode.getText())){
                                Log.v("third", "kod jest ok ");
                                if(genderRadioGroup.getCheckedRadioButtonId() == -1){
                                    Log.v("third", "no radio button are checked");
                                }else{
                                    Log.v("third", "radio button are checked");
                                    if(rulesCheckBox.isChecked()){
                                        Log.v("third", "checkbox is checked");
                                        Log.v("third", "POPRAWNIE WYKONANO WSZYSTKIE KROTKI! MOZNA REJSTROWAĆ ");
                                        Log.v("third", "gender: " +  gender);
                                         new registerNewUser(StartActivity.accountRegistration_fID, myPrefsRegister.getString("cardNumber", ""), gender,
                                                 textViewDateOfBirth.getText().toString(), edittextZipCode.getText().toString(), myPrefsRegister.getString("phone", ""),
                                                 myPrefsRegister.getString("email", ""), editTextName.getText().toString()).execute();
                                    }else{
                                        Log.v("third", "checkbox isn't checked");
                                    }
                                }
                            }else{
                                Log.v("third", "kod nie jest ok");
                            }
                        } else {
                            Log.v("third", "wiek < 16");
                        }
                    } else {
                        Log.v("third", "wiek jest pusty");
                    }
                }
            }
        });
    }

    public String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month-1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        myAge = ageInt;

        return ageS;
    }



    public class registerNewUser extends AsyncTask<String, String, String> {

        Connection_API C_api = new Connection_API(SignupActivity_ThirdStep.this);

        private String p_fID;
        private String p_uID;
        private String p_p1;
        private String p_p2;
        private String p_p3;
        private String p_p4;
        private String p_p5;
        private String p_p6;

        registerNewUser(String fID, String cardNumber, String gender, String dateOfBirth, String zipCode, String phone, String email, String name){
            this.p_fID = fID;
            this.p_uID = cardNumber;
            this.p_p1 = gender;
            this.p_p2 = dateOfBirth;
            this.p_p3 = zipCode;
            this.p_p4 = phone;
            this.p_p5 = email;
            this.p_p6 = name;
        }

        @Override
        protected String doInBackground(String... strings) {
            return C_api.registration_API(p_fID, p_uID, p_p1, p_p2, p_p3, p_p4, p_p5, p_p6);
        }

        @Override
        protected void onPostExecute(String result) {
            Parser par = new Parser();
            Document doc = par.getDocument(result);



            Intent intent = new Intent(SignupActivity_ThirdStep.this, SignupActivity_FourthStep.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SignupActivity_ThirdStep.this, transLayout, ViewCompat.getTransitionName(transLayout));
            startActivity(intent, options.toBundle());
        }
    }
}
