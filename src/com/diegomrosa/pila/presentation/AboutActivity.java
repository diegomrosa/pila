package com.diegomrosa.pila.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import com.diegomrosa.pila.R;

public class AboutActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        WebView view = (WebView) findViewById(R.id.webView);

        view.loadUrl(getString(R.string.aboutUri));
    }
}
