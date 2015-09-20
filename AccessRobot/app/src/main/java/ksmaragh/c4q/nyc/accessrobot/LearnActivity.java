package ksmaragh.c4q.nyc.accessrobot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LearnActivity extends AppCompatActivity {
    @Bind(R.id.btn_learn_assembly)
    Button btnAssemblyVideo;

    @Bind(R.id.btn_learn_mozi_components)
    Button btnLearnMoziComponents;

    @Bind(R.id.btn_order_parts)
    Button btnOrderParts;

    @Bind(R.id.btn_learn_blockly)
    Button btnLearnBlockly;


    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_learn_mozi_components:
                    startComponentListActivity();
                    break;

                case R.id.btn_order_parts:
                    startPartsActivity();
                    break;

                case R.id.btn_learn_assembly:
                    playMoziAssemblyVideo();
                    break;

                case R.id.btn_learn_blockly:
                    startBlocklyTutorial();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        ButterKnife.bind(this);
        initActionBar();


        btnLearnMoziComponents.setOnClickListener(buttonClickListener);
        btnOrderParts.setOnClickListener(buttonClickListener);
        btnAssemblyVideo.setOnClickListener(buttonClickListener);
        btnLearnBlockly.setOnClickListener(buttonClickListener);


    }

    private void initActionBar() {

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setText("Build")
                .setTabListener(new TabListener(
                        this, "build"));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText("Learn")
                .setTabListener(new TabListener(
                        this, "learn"));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText("Program")
                .setTabListener(new TabListener(
                        this, "program"));
        actionBar.addTab(tab);

    }

    private void startComponentListActivity() {
        Intent intent = new Intent(this, ComponentListActivity.class);
        startActivity(intent);
    }

    private void startPartsActivity() {
        Intent intent = new Intent(this, PartsActivity.class);
        startActivity(intent);

    }

    private void playMoziAssemblyVideo() {
        String videoId = "DXpl0H1tLcU";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("force_fullscreen", true);
        startActivity(intent);

    }

    private void startBlocklyTutorial() {
        Intent intent = new Intent(this, MoziActivity.class);
        startActivity(intent);

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

    public static class TabListener implements ActionBar.TabListener {
        private final LearnActivity mActivity;
        private final String mTag;


        public TabListener(LearnActivity activity, String tag) {
            mActivity = activity;
            mTag = tag;
        }

    /* The following are each of the ActionBar.TabListener callbacks */


        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            // Check if the fragment is already initialized

            if (mTag.equals("build")) {

            } else if (mTag.equals("learn")) {
                Intent intent = new Intent(mActivity, MoziActivity.class);
                intent.putExtra("tutorial", true);
                mActivity.startActivity(intent);
            } else if (mTag.equals("program")) {
                Intent intent = new Intent(mActivity, MoziActivity.class);
                intent.putExtra("tutorial", false);
                mActivity.startActivity(intent);
            }
        }

        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }
    }
}
