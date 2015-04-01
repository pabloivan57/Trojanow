package com.trojanow.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pabloivan57.trojanow.R;

public class LoginController extends ActionBarActivity {

    EditText txtEmail;
    EditText txtPassword;
    Button   btnLogin;
    TextView linkSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_controller);

        txtEmail    = (EditText) findViewById(R.id.txtLoginEmail);
        txtPassword = (EditText) findViewById(R.id.txtLoginPassword);

        btnLogin    = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLoginHandler);

        linkSignup  = (TextView) findViewById(R.id.linkSignup);
        linkSignup.setOnClickListener(linkSignupHandler);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_controller, menu);
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
     * Login Handler, it will call the API AuthService to do a new login operation
     */
    View.OnClickListener btnLoginHandler = new View.OnClickListener(){
      public void onClick(View v) {
          Intent intent = new Intent(v.getContext(), PostController.class);
          startActivityForResult(intent, 0);
      }
    };

    /**
     * Signup Handler, launches Signup screen Intent
     */
    View.OnClickListener linkSignupHandler = new View.OnClickListener(){
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), SignupController.class);
            startActivityForResult(intent, 0);
        }
    };
}
