package com.vilect.asm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vilect.asm.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        wv = findViewById(R.id.wv);
        String link = getIntent().getExtras().getString("link");
        wv.loadUrl(link);


    }
}
