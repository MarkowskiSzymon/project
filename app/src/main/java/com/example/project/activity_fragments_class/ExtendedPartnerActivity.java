package com.example.project.activity_fragments_class;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.project.R;
import com.squareup.picasso.Picasso;



public class ExtendedPartnerActivity extends AppCompatActivity {

    private ImageView partnerLogo;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_partner);

        toolbar = findViewById(R.id.ext_tooolbar);
        partnerLogo = findViewById(R.id.imageView_activtiyExtendedPartner_partnerLogo);


        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Picasso.get()
                .load(StartActivity.partners_layout_url + getIntent().getStringExtra("partnerLogo"))
                .placeholder(R.drawable.error_image)
                .fit()
                .into(partnerLogo);
    }
}