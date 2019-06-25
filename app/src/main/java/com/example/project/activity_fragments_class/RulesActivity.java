package com.example.project.activity_fragments_class;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toolbar;

import com.example.project.R;

public class RulesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        toolbar = findViewById(R.id.toolbar_activityRules);
        webView = findViewById(R.id.webview_activityRules);

        toolbar.setTitle("Regulamin");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();

            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(StartActivity.dellyRulesUrl);
    }

}
