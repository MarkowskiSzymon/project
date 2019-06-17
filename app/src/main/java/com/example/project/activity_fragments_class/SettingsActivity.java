package com.example.project.activity_fragments_class;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.project.R;
import com.example.project.model.LoginModel;


public class SettingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView text_dummy_hint_username, text_dummy_hint_date, text_dummy_hint_zipCode;
    private EditText editUsername, editDate, editZipCode;
    private RadioButton radioButtonMale, radioButtonFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        LoginModel loginModel = new LoginModel();
        toolbar = findViewById(R.id.toolbar_activitySetting);
        text_dummy_hint_username = findViewById(R.id.text_dummy_hint_username);
        text_dummy_hint_date = findViewById(R.id.text_dummy_hint_date);
        text_dummy_hint_zipCode = findViewById(R.id.text_dummy_hint_zipCode);

        editUsername = findViewById(R.id.editText_activitySettings_username);
        editDate = findViewById(R.id.editText_activitySettings_date);
        editZipCode = findViewById(R.id.editText_activitySettings_zipCode);

        radioButtonFemale = findViewById(R.id.radiobutton_activitySettings_female);
        radioButtonMale = findViewById(R.id.radiobutton_activitySettings_male);


        editDate.setText(loginModel.getDate_of_birth());

        if(editDate.getText().length() > 0 ){
            text_dummy_hint_date.setVisibility(View.VISIBLE);
        }




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


        editDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            text_dummy_hint_date.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editDate.getText().length() > 0)
                        text_dummy_hint_date.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_date.setVisibility(View.INVISIBLE);
                }
            }
        });

        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_zipCode.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (editZipCode.getText().length() > 0)
                        text_dummy_hint_zipCode.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_zipCode.setVisibility(View.INVISIBLE);
                }
            }
        });




        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
}
