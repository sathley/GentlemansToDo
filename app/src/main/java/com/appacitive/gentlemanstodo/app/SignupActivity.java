package com.appacitive.gentlemanstodo.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appacitive.core.AppacitiveUser;
import com.appacitive.core.model.Callback;


public class SignupActivity extends ActionBarActivity {

    EditText name;
    EditText email;
    EditText password;
    Button signup;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.signupName);
        email = (EditText) findViewById(R.id.signupEmail);
        password = (EditText) findViewById(R.id.signupPassword);

        signup = (Button) findViewById(R.id.signup);
        context = this;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = name.getText().toString();
                String emailAddress = email.getText().toString();
                String passwordString = password.getText().toString();

                //  Add user signup code here

            }
        });
    }
}
