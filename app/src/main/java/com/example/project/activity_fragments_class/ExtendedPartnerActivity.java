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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ExtendedPartnerActivity extends AppCompatActivity {

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
        Spanned htmlAsSpanned = Html.fromHtml(descHtml);
        String html = "Męski salon fryzjerski w klimatycznym lokalu!&#38;#60;br&#38;#62;&#38;#60;b&#38;#62;Barber Lot&#38;#60;&#38;#47;b&#38;#62; to pełen profesjonalizm, miła atmosfera oraz indywidualne podejście do każdego klienta.&#38;#60;br&#38;#62;&#38;#60;br&#38;#62;&#38;#60;a href=&#38;#34;https:&#38;#47;&#38;#47;delly.pl&#38;#47;partner&#38;#47;246&#38;#47;Barber%20Lot%20Batorego&#38;#34;&#38;#62;Tutaj znajdziesz listę nagród&#38;#60;a&#38;#62;";
        String desc = descHtml.replace("&#38;#60;", "<").replace("&#38;#62;", ">").replace("&#38;#34;", "\"").replace("&#38;#47;", "/");
        description.setMovementMethod(LinkMovementMethod.getInstance());
        description.setText(Html.fromHtml(desc));
    }
}