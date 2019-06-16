package com.example.project.activity_fragments_class.Registration;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.R;
import com.example.project.activity_fragments_class.StartActivity;

public class SignupActivity_FourthStep extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private SharedPreferences myPrefsRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_fourth_step);

        lottieAnimationView = findViewById(R.id.animacjaFourth);
        myPrefsRegister = getSharedPreferences(StartActivity.SharedP_REGISTER, MODE_PRIVATE);
        lottieAnimationView.setProgress(Float.parseFloat(myPrefsRegister.getString("frame", "")));
        lottieAnimationView.playAnimation();

    }
}



