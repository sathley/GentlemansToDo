package com.appacitive.gentlemanstodo.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appacitive.android.AppacitiveContext;
import com.appacitive.core.AppacitiveUser;
import com.appacitive.core.model.Callback;
import com.appacitive.core.model.Environment;


public class LoginActivity extends ActionBarActivity {

    EditText email;
    EditText password;
    Button login;
    Button signup;
    Context context;

    private static final String TAG = "LoginFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //  Add AppacitiveContext initialize code here
        AppacitiveContext.initialize("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", Environment.sandbox, this);

        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);
        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);
        context = this;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = email.getText().toString();
                String passwordString = password.getText().toString();

                //  Add user login code here

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(context, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}
