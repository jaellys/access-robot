package ksmaragh.c4q.nyc.accessrobot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PartsActivity extends AppCompatActivity {

    @Bind(R.id.partsWebView)
    WebView partsWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);
        ButterKnife.bind(this);

        String url = "http://www.amazon.com/gp/aws/cart/add.html?" +
                "AssociateTag=your-tag-here-20&" +
                "ASIN.1=B00OBSD202&Quantity.1=1&" +
                "ASIN.2=B00P7Q86HG&Quantity.2=1&" +
                "ASIN.3=B00KTXWG9G&Quantity.3=1&" +
                "ASIN.4=B001CFUBN8&Quantity.4=2&" +
                "ASIN.5=B0089VA3AY&Quantity.5=1&" +
                "ASIN.6=B00C0Q67IQ&Quantity.6=1&" +
                "ASIN.7=B0081IC18W&Quantity.7=1&" +
                "ASIN.8=B000TGSPV6&Quantity.8=1&" +
                "ASIN.9=B00AYPEL56&Quantity.9=1";

        partsWebView.getSettings().setJavaScriptEnabled(true);
        partsWebView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
