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

public class RegisterActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        createNavigation();
    }

    public void register(View view) throws IOException {

        final EditText Username = (EditText) findViewById(R.id.username);
        final EditText Password = (EditText) findViewById(R.id.password);
        EditText Re_Password = (EditText) findViewById(R.id.re_password);
        EditText FirstName = (EditText) findViewById(R.id.firstName);
        EditText LastName = (EditText) findViewById(R.id.lastName);
        final EditText NIC = (EditText) findViewById(R.id.nic);
        EditText Email = (EditText) findViewById(R.id.email);
        EditText Contact = (EditText) findViewById(R.id.contact);
        EditText Address = (EditText) findViewById(R.id.address);

        String un = Username.getText().toString();
        String pwd = Password.getText().toString();
        String re_pwd = Re_Password.getText().toString();
        String fn = FirstName.getText().toString();
        String ln = LastName.getText().toString();
        String nic = NIC.getText().toString();
        String email = Email.getText().toString();
        String cn = Contact.getText().toString();
        String add = Address.getText().toString();

        if (un.trim().equals("")) {
            Username.setError("Please enter Username...!");
        } else if (pwd.trim().equals("")) {
            Password.setError("Please enter Password...!");
        } else if (re_pwd.trim().equals("")) {
            Re_Password.setError("Please re-enter password...!");
        } else if (fn.trim().equals("")) {
            FirstName.setError("Please enter First Name...!");
        } else if (ln.trim().equals("")) {
            LastName.setError("Please enter Last Name...!");
        } else if (nic.trim().equals("")) {
            NIC.setError("Please re-enter NIC Number...!");
        } else if (email.trim().equals("")) {
            Email.setError("Please enter Email Address...!");
        } else if (cn.trim().equals("")) {
            Contact.setError("Please enter Contact Number...!");
        } else if (add.trim().equals("")) {
            Address.setError("Please enter Postal Address...!");
        } else {

            User user = new User();
            String result = user.registerUser(un, pwd, re_pwd, fn, ln, nic, email, cn, add, this);

            if ("Registered".equals(result)) {
                Toast.makeText(this, R.string.suc_reg, Toast.LENGTH_SHORT).show();
                ManageSession manageSession = new ManageSession(this);
                manageSession.setUsername(un);
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }

            if ("Username exist".equals(result)) {
                Toast.makeText(this, R.string.err_inuse_username, Toast.LENGTH_SHORT).show();
            }

            if ("Mis-matching passwords".equals(result)) {
                Toast.makeText(this, R.string.err_invalid_repassword, Toast.LENGTH_SHORT).show();
            }

            if ("Invalid email format".equals(result)) {
                Toast.makeText(this, R.string.err_invalid_email, Toast.LENGTH_SHORT).show();
            }

            if ("Email exist".equals(result)) {
                Toast.makeText(this, R.string.err_inuse_email, Toast.LENGTH_SHORT).show();
            }

            if ("Invalid contact number format".equals(result)) {
                Toast.makeText(this, R.string.err_invalid_contact, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
