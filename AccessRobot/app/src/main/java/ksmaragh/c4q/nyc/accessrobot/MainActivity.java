package ksmaragh.c4q.nyc.accessrobot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.btn_build)
    MaterialIconView btnBuild;
    @Bind(R.id.btn_learn)
    MaterialIconView btnLearn;
    @Bind(R.id.btn_parts)
    MaterialIconView btnParts;
    @Bind(R.id.btn_program)
    MaterialIconView btnProgram;
    @Bind(R.id.tv_build)
    TextView tvBuild;
    @Bind(R.id.tv_learn)
    TextView tvLearn;
    @Bind(R.id.tv_parts)
    TextView tvParts;
    @Bind(R.id.tv_program)
    TextView tvProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_build, R.id.btn_learn, R.id.btn_parts, R.id.btn_program})
    protected void launchActivity(View v) {
        switch (v.getId()) {
            case R.id.btn_build:
                Intent build = new Intent(MainActivity.this, BuildActivity.class);
                MainActivity.this.startActivity(build);
                break;
            case R.id.btn_learn:
                Intent learn = new Intent(MainActivity.this, LearnActivity.class);
                MainActivity.this.startActivity(learn);
                break;
            case R.id.btn_parts:
                Intent parts = new Intent(MainActivity.this, PartsActivity.class);
                MainActivity.this.startActivity(parts);
                break;
            case R.id.btn_program:
                Intent program = new Intent(MainActivity.this, CodeActivity.class);
                MainActivity.this.startActivity(program);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
