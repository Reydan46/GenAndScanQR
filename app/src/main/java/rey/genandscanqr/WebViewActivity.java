package rey.genandscanqr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.*;

public class WebViewActivity extends AppCompatActivity
{

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }


    // Запрет возврата назад
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, ScanQR.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView wv_page = findViewById(R.id.wv_page);
        wv_page.getSettings().setJavaScriptEnabled(true);
        wv_page.getSettings().setDomStorageEnabled(true);
        wv_page.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_page.setWebChromeClient(new WebChromeClient());

        wv_page.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(bundle.getString("Title"));
                getSupportActionBar().setSubtitle(bundle.getString("Date"));
            }
            wv_page.loadUrl(bundle.getString("URL"));
        }
    }

}
