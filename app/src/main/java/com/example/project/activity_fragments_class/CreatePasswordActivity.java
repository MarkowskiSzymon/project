package com.example.project.activity_fragments_class;

import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.project.R;

public class CreatePasswordActivity extends AppCompatActivity {

    private TextInputLayout textinputlayout_activityCreatePassword_password, textinputlayout_activityCreatePassword_passwordRepeat;
    private TextView text_dummy_hint_password, text_dummy_hint_passwordRepeat;
    private EditText editText_password, editText_passwordRepeat;
    private RelativeLayout tranLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);


        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
       // fade.excludeTarget(decor.findViewById(R.id.toolbar), true);
        fade.excludeTarget(findViewById(R.id.toolbar), true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        tranLayout = findViewById(R.id.layout_transiston_create);

        textinputlayout_activityCreatePassword_password = findViewById(R.id.textinputlayout_activityCreatePassword_password);
        textinputlayout_activityCreatePassword_passwordRepeat = findViewById(R.id.textinputlayout_activityCreatePassword_passwordRepeat);

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

    }
}
