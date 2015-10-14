package ksmaragh.c4q.nyc.accessrobot;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;


public class LoginActivity extends ActionBarActivity {
    private LoginActivity activity = this;
    private EditText etEmail;
    private EditText etPassword;
    private Button loginButton;
    private Button signupButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If a user is logged in, go directly to the inventory list
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        if (currentUser.get("name") != null) {
//            Intent intent = new Intent(this, MoziActivity.class);
//            startActivity(intent);
//        }

        setContentView(R.layout.activity_login);
        initializeView();
    }

    private void initializeView() {
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        signupButton = (Button) findViewById(R.id.signup_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                progressBar.setVisibility(View.VISIBLE);
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                login(email, password);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                progressBar.setVisibility(View.VISIBLE);
                String email = etEmail.getText().toString();
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void login(String email, String password) {
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Intent intent = new Intent(getBaseContext(), MoziActivity.class);
                    intent.putExtra("tutorial", false);
                    startActivity(intent);
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    progressBar.setVisibility(View.INVISIBLE);
                    if (e.toString().contains("invalid login parameters")) {
                        Toast.makeText(getBaseContext(), "Wrong username or password.", Toast.LENGTH_LONG).show();
                    } else if (e.toString().contains("i/o failure")) {
                        Toast.makeText(getBaseContext(), "No network connection.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.reset_password) {
            progressBar.setVisibility(View.VISIBLE);
            final String email = etEmail.getText().toString();
            ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (e == null) {
                        // An email was successfully sent with reset instructions
                        Toast.makeText(getBaseContext(), "An email has been sent to " + email + " with reset instructions.", Toast.LENGTH_LONG).show();
                    } else if (e.toString().contains("i/o failure")) {
                        Toast.makeText(getBaseContext(), "No network connection.", Toast.LENGTH_LONG).show();
                    } else if (e.toString().contains("invalid email address")) {
                        Toast.makeText(getBaseContext(), "Invalid email address. Sign up to create an account.", Toast.LENGTH_LONG).show();
                    } else if (e.toString().contains("provide an email")) {
                        Toast.makeText(getBaseContext(), "You must provide an email.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
