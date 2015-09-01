package ksmaragh.c4q.nyc.accessrobot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;


public class SignupActivity extends ActionBarActivity {
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etName;
    private EditText etAge;
    private Button signupButton;
    private RelativeLayout background;

    private String intentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = getIntent();
        intentEmail = intent.getStringExtra("email");
        initializeView();
        ParseUser.logOut();

    }

    private void initializeView() {
        etEmail = (EditText) findViewById(R.id.email);
        if (intentEmail != null) {
            etEmail.setText(intentEmail);
        }
        background = (RelativeLayout) findViewById(R.id.layout_signup);
        background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });
        etPassword = (EditText) findViewById(R.id.password);
        etConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        etName = (EditText) findViewById(R.id.name);
        etAge = (EditText) findViewById(R.id.age);
        signupButton = (Button) findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                signup(email, password, confirmPassword, name, age);
            }
        });
    }

    private void signup(String email, String password, String confirmPassword, String name, String age) {

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getBaseContext(), "Password doesn't match.", Toast.LENGTH_LONG).show();
            return;
        }

        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.put("name", name);
        user.put("age", age);
        user.put("level", 0);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(getBaseContext(), "Welcome!" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), MoziActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    if (e.toString().contains("invalid email")) {
                        Toast.makeText(getBaseContext(), "Please enter a valid email.", Toast.LENGTH_LONG).show();
                    } else if (e.toString().contains("i/o failure")) {
                        Toast.makeText(getBaseContext(), "No network connection.", Toast.LENGTH_LONG).show();
                    } else if (e.toString().contains("Password cannot be missing or blank")) {
                        Toast.makeText(getBaseContext(), "Password cannot be missing or blank.", Toast.LENGTH_LONG).show();
                    } else if (e.toString().contains("already taken")) {
                        Toast.makeText(getBaseContext(), "An account already exists for this email. If you've forgotten your password, select 'Reset password' from the top menu.", Toast.LENGTH_LONG).show();
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
            //progressBar.setVisibility(View.VISIBLE);
            final String email = etEmail.getText().toString();
            ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    //progressBar.setVisibility(View.INVISIBLE);
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
