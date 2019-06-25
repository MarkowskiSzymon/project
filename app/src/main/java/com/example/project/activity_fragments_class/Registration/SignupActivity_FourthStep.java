package com.example.project.activity_fragments_class.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.project.R;
import com.example.project.activity_fragments_class.LoginActivity;

public class SignupActivity_FourthStep extends AppCompatActivity {

    private Button button;
    private RelativeLayout transLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_fourth_step);

        button = findViewById(R.id.button_activitySignupFourthStep);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transLayout = findViewById(R.id.layout_activitySignupFourthStep_transiston_create);
                Intent intent = new Intent(SignupActivity_FourthStep.this, LoginActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SignupActivity_FourthStep.this, transLayout, ViewCompat.getTransitionName(transLayout));
                startActivity(intent, options.toBundle());
                finish();
            }
        });

    }
}



