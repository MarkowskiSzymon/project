package com.example.project.activity_fragments_class;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.Utils.Regex_patterns;

public class CreatePasswordActivity extends AppCompatActivity {

    private TextView text_dummy_hint_password, text_dummy_hint_passwordRepeat;
    private EditText editText_password, editText_passwordRepeat;
    private Button button_savePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        final Regex_patterns regex_patterns = new Regex_patterns();

        button_savePassword = findViewById(R.id.button_activityCreatePassword_buttonSavePassword);

        text_dummy_hint_password = findViewById(R.id.textView_activityCreatePassword_dummyHintPassword);
        text_dummy_hint_passwordRepeat = findViewById(R.id.textView_activityCreatePassword_dummyHintPasswordRepeat);

        editText_password = findViewById(R.id.editText_activityCreatePassword_password);
        editText_passwordRepeat = findViewById(R.id.editText_activityCreatePassword_passwordRepeat);

        editText_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                    if (editText_password.getText().length() > 0){
                        text_dummy_hint_password.setVisibility(View.VISIBLE);
                    } else{
                        text_dummy_hint_password.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        editText_passwordRepeat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            text_dummy_hint_passwordRepeat.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    if (editText_passwordRepeat.getText().length() > 0){
                        text_dummy_hint_passwordRepeat.setVisibility(View.VISIBLE);
                    } else{
                        text_dummy_hint_passwordRepeat.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        button_savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("newpassword", "nacisniety");
                if(!regex_patterns.isPasswordValid(editText_password.getText()) || editText_password.getText().toString().contains(" ")){
                    Log.v("newpassword", "NIEPOPRAWNE");
                }else{
                    if(!editText_password.getText().toString().equals(editText_passwordRepeat.getText().toString())){
                        Log.v("newpassword", "Hasla sie nie zgadzaja");
                    }else {
                        Log.v("newpassword", "WSZYSTKO JEST OK, WYSYLAM ZAPYTANIE DO API O ZMIANE HASŁA");

                        // WSZYSTKO JEST OK, WYSYLAM ZAPYTANIE DO API O ZMIANE HASŁA
                    }


                }
            }
        });

    }
}
