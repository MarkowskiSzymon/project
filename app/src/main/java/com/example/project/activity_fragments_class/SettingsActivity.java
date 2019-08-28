package com.example.project.activity_fragments_class;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.project.R;
import com.example.project.Utils.Regex_patterns;
import com.example.project.model.LoginModelTest;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;


public class SettingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView text_dummy_hint_username, text_dummy_hint_dateOfBirth, text_dummy_hint_zipCode, text_dummy_hint_email, text_dummy_hint_phoneNumber, text_dummy_hint_password, text_dummy_hint_repeatPassword;
    private EditText editUsername, editDateOfBirth, editZipCode, editEmail, editPhoneNumber, editPassword, editRepeatPassword;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Integer myAge;
    private SharedPreferences myPrefs;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button buttonUpdateNewData, buttonSaveNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDellyGradientBlue));
        setContentView(R.layout.activity_settings);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initialize();

        LoginModelTest loginModelTest = new LoginModelTest();
        myPrefs = getSharedPreferences(StartActivity.SharedP_LOGIN, Context.MODE_PRIVATE);
        editPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        editPhoneNumber.setTransformationMethod(null);
        editUsername.setText(loginModelTest.getImie());
        editDateOfBirth.setText(loginModelTest.getData_urodzenia());
        editZipCode.setText(loginModelTest.getKod_pocztowy());
        editEmail.setText(loginModelTest.getEmail());
        editPhoneNumber.setText(loginModelTest.getTelefon());

        if(loginModelTest.listOfInformation.get(0).getPlec().equals("M")){
            radioButtonMale.setChecked(true);
        } else{
            radioButtonFemale.setChecked(true);
        }

        if(editDateOfBirth.getText().length() > 0 ){
            text_dummy_hint_dateOfBirth.setVisibility(View.VISIBLE);
        }
        if(editUsername.getText().length() > 0){
            text_dummy_hint_username.setVisibility(View.VISIBLE);
        }
        if(editZipCode.getText().length() > 0){
            text_dummy_hint_zipCode.setVisibility(View.VISIBLE);
        }
        if(editEmail.getText().length() > 0){
            text_dummy_hint_email.setVisibility(View.VISIBLE);
        }
        if(editPhoneNumber.getText().length() > 0){
            text_dummy_hint_phoneNumber.setVisibility(View.VISIBLE);
        }


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            text_dummy_hint_username.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editUsername.getText().length() > 0)
                        text_dummy_hint_username.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_username.setVisibility(View.INVISIBLE);
                }
            }
        });


        editDateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_dummy_hint_dateOfBirth.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editDateOfBirth.getText().length() > 0)
                        text_dummy_hint_dateOfBirth.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_dateOfBirth.setVisibility(View.INVISIBLE);
                }
            }
        });

        editDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        SettingsActivity.this,
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
                editDateOfBirth.setText(date);
            }
        };

        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_dummy_hint_zipCode.setVisibility(View.VISIBLE);
                            editZipCode.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (!s.toString().contains("-") && s.length() > 2) {
                                        s.insert(2, "-");
                                    }
                                }
                            });
                        }
                    }, 100);
                } else {
                    if (editZipCode.getText().length() > 0) {
                        text_dummy_hint_zipCode.setVisibility(View.VISIBLE);
                    } else {
                        text_dummy_hint_zipCode.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        editEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                    if (editEmail.getText().length() > 0)
                        text_dummy_hint_email.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_email.setVisibility(View.INVISIBLE);
                }
            }
        });

        editPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_dummy_hint_phoneNumber.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editPhoneNumber.getText().length() > 0)
                        text_dummy_hint_phoneNumber.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_phoneNumber.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonUpdateNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_dummy_hint_password.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editPassword.getText().length() > 0)
                        text_dummy_hint_password.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_password.setVisibility(View.INVISIBLE);
                }
            }
        });

        editRepeatPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_dummy_hint_repeatPassword.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editRepeatPassword.getText().length() > 0)
                        text_dummy_hint_repeatPassword.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_repeatPassword.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonSaveNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar_activitySetting);
        editPhoneNumber = findViewById(R.id.editText_activitySettings_phoneNumber);
        editPassword = findViewById(R.id.editText_activitySettings_password);
        editRepeatPassword = findViewById(R.id.editText_activitySettings_repeatPassword);
        radioButtonFemale = findViewById(R.id.radiobutton_activitySettings_female);
        radioButtonMale = findViewById(R.id.radiobutton_activitySettings_male);
        buttonSaveNewPassword = findViewById(R.id.button_activitySettings_saveNewPassword);
        buttonUpdateNewData = findViewById(R.id.button_activitySettings_updateNewData);
        text_dummy_hint_username = findViewById(R.id.textView_activitySettings_dummyHintUsername);
        text_dummy_hint_dateOfBirth = findViewById(R.id.textView_activitySettings_dummyHintDateOfBirth);
        text_dummy_hint_zipCode = findViewById(R.id.textView_activitySettings_dummyHintZipCode);
        text_dummy_hint_email = findViewById(R.id.textView_activitySettings_dummyHintEmail);
        text_dummy_hint_phoneNumber = findViewById(R.id.textView_activitySettings_dummyPhoneNumber);
        text_dummy_hint_password = findViewById(R.id.textView_activitySettings_dummyHintPassword);
        text_dummy_hint_repeatPassword = findViewById(R.id.textView_activitySettings_dummyHintRepeatPassword);
        editUsername = findViewById(R.id.editText_activitySettings_username);
        editDateOfBirth = findViewById(R.id.editText_activitySettings_date);
        editZipCode = findViewById(R.id.editText_activitySettings_zipCode);
        editEmail = findViewById(R.id.editText_activitySettings_email);
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
}
