package com.trojanow.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pabloivan57.trojanow.R;

public class SignupController extends ActionBarActivity {

    EditText txtSignupFullname;
    EditText txtSignupEmail;
    EditText txtSignupPassword;
    Button   btnSignup;
    TextView linkLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_controller);

        txtSignupFullname = (EditText) findViewById(R.id.txtSignupFullname);
        txtSignupEmail    = (EditText) findViewById(R.id.txtSignupEmail);
        txtSignupPassword = (EditText) findViewById(R.id.txtLoginPassword);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(btnSignupHandler);

        linkLogin = (TextView) findViewById(R.id.linkLogin);
        linkLogin.setOnClickListener(linkLoginHandler);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup_controller, menu);
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

    /**
     * Signup Handler, it will call the API AuthService to do a new login operation
     */
    View.OnClickListener btnSignupHandler = new View.OnClickListener(){
        public void onClick(View v) {

        }
    };

    /**
     * Login Handler, it will go back to login Intent
     */

    View.OnClickListener linkLoginHandler = new View.OnClickListener(){
        public void onClick(View v) {
            SignupController.this.finish();
        }
    };
}
