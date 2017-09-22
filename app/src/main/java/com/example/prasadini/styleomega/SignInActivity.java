package com.example.prasadini.styleomega;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prasadini.styleomega.model.ManageSession;
import com.example.prasadini.styleomega.model.Navigation;
import com.example.prasadini.styleomega.model.User;

import java.io.IOException;

public class SignInActivity extends Navigation {

    // Might not need this.. public static final String MESSAGE = "com.example.styleomega.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        createNavigation();
    }

    public void login(View view) throws IOException {
        EditText Username = (EditText)findViewById(R.id.username);
        EditText Password = (EditText)findViewById(R.id.password);
        String un = Username.getText().toString();
        String pwd = Password.getText().toString();

        if(un.trim().equals("")){
            Username.setError("Please enter Username...!");
        } else if (pwd.trim().equals("")){
            Password.setError("Please enter Password...!");
        } else {

            User user = new User();
            String result = user.validateLogin(un, pwd, this);

             if (result.equals("Authenticated")) {
                ManageSession manageSession = new ManageSession(this);
                manageSession.setUsername(un);
                Toast.makeText(this, R.string.suc_login, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            if (result.equals("Not Authenticated")) {
                Toast.makeText(this, R.string.err_invalid_unORpwd, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Calls when the user taps on Register Button */
    public void gotoRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
