package com.example.project.activity_fragments_class;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.Utils.Connection_API;
import com.example.project.Utils.Connection_INTERNET;
import com.example.project.Utils.Parser;
import com.example.project.Utils.Regex_patterns;
import com.example.project.model.LoginModelTest;

import org.w3c.dom.Document;

public class CreatePasswordActivity extends AppCompatActivity {

    private TextView text_dummy_hint_password, text_dummy_hint_passwordRepeat;
    private EditText editText_password, editText_passwordRepeat, editText_oldPassword;
    private Button button_savePassword;
    private Connection_INTERNET conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        initialize();
        final Regex_patterns regex_patterns = new Regex_patterns();
        conn = new Connection_INTERNET(getApplicationContext());

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
                if(!regex_patterns.isPasswordValid(editText_password.getText()) || editText_password.getText().toString().contains(" ")){
                    editText_password.hasFocus();
                } else{
                    if(!editText_password.getText().toString().equals(editText_passwordRepeat.getText().toString())){
                        editText_password.hasFocus();
                    } else {
                        new createNewPassword(StartActivity.createNewFirstPassword_fID, conn.getDeviceId(), editText_oldPassword.getText().toString(), editText_password.getText().toString()).execute();
                    }
                }
            }
        });
    }

    private void initialize() {
        button_savePassword = findViewById(R.id.button_activityCreatePassword_buttonSavePassword);
        text_dummy_hint_password = findViewById(R.id.textView_activityCreatePassword_dummyHintPassword);
        text_dummy_hint_passwordRepeat = findViewById(R.id.textView_activityCreatePassword_dummyHintPasswordRepeat);
        editText_password = findViewById(R.id.editText_activityCreatePassword_password);
        editText_password = findViewById(R.id.editText_activityCreatePassword_password);
        editText_passwordRepeat = findViewById(R.id.editText_activityCreatePassword_passwordRepeat);
        editText_oldPassword = findViewById(R.id.editText_activityCreatePassword_oldPassword);
    }

    public class createNewPassword extends AsyncTask<String, String, String> {
        Connection_API C_api = new Connection_API(CreatePasswordActivity.this);

        private String pTabid;
        private String pfID;
        private String pusername;
        private String ppassword;

        createNewPassword(String Tabid, String fID, String username, String password) {
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

        }
    }
}
