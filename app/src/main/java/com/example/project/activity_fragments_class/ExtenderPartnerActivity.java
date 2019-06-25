package com.example.project.activity_fragments_class;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.widget.ImageView;

import com.example.project.R;
import com.example.project.Utils.Adaptery.picasso_rounded_corners;
import com.squareup.picasso.Picasso;


public class ExtenderPartnerActivity extends AppCompatActivity {

    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extender_partner);

        image = findViewById(R.id.imageExtended);


        Log.v("App", "przychodzace zdjecie: " + getIntent().getStringExtra("partnerLogo"));



        Picasso.get()
                .load(StartActivity.partners_layout_url + getIntent().getStringExtra("partnerLogo"))
                .placeholder(R.drawable.error_image)
                .fit()
                .into(image);
    }

}