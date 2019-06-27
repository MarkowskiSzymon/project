package com.example.project.activity_fragments_class;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.model.PartnersModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ExtendedPartnerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView partnerLogo;
    private Toolbar toolbar;
    private TextView partnerName, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_partner);

        PartnersModel partnersModel = new PartnersModel();
        String position = getIntent().getStringExtra("position");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        toolbar = findViewById(R.id.ext_tooolbar);
        partnerLogo = findViewById(R.id.imageView_activtiyExtendedPartner_partnerLogo);
        partnerName = findViewById(R.id.textView_activityExtendedPartner_partnerName);
        description = findViewById(R.id.textView_activityExtendedPartner_description);

        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Picasso.get()
                .load(StartActivity.partners_layout_url  + partnersModel.listOfPartners.get(Integer.parseInt(position)).getPic())
                .into(partnerLogo);

        partnerName.setText(partnersModel.listOfPartners.get(Integer.parseInt(position)).getName());

        String descHtml = partnersModel.listOfPartners.get(Integer.parseInt(position)).getOpis();
        String desc = descHtml.replaceAll("&#60;", "<").replaceAll("&#62;", ">").replaceAll("&#34;", "\"").replaceAll("&#47;", "/");

        description.setMovementMethod(LinkMovementMethod.getInstance());
        description.setText(Html.fromHtml(desc));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(desc));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_activtiyExtendedPartner);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        PartnersModel partnersModel = new PartnersModel();
        String position = getIntent().getStringExtra("position");

        LatLng partnerLocation = new LatLng(Double.parseDouble(partnersModel.listOfPartners.get(Integer.parseInt(position)).getAlt()), Double.parseDouble(partnersModel.listOfPartners.get(Integer.parseInt(position)).getLat()));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(partnerLocation)
                .zoom(14).build();
        googleMap.addMarker(new MarkerOptions().position(partnerLocation).title(partnersModel.listOfPartners.get(Integer.parseInt(position)).getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(partnerLocation));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}