package com.diegomrosa.pila.apresentacao;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import com.diegomrosa.pila.R;

public class SobreActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre);
        WebView view = (WebView) findViewById(R.id.webView);

        view.loadUrl("file:///android_asset/sobre.html");
    }
}
