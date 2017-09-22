package com.example.prasadini.styleomega;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prasadini.styleomega.model.ManageSession;
import com.example.prasadini.styleomega.model.Navigation;
import com.example.prasadini.styleomega.model.User;
import com.example.prasadini.styleomega.model.UserBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageAccountActivity extends Navigation {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_account);
        createNavigation();

        ManageSession manageSession = new ManageSession(this);
        String username = manageSession.getUsername();
        User user = new User();
        try {
            ArrayList<UserBean> editInfoList = user.userDetails(username,this);
            EditText FirstName = (EditText)findViewById(R.id.firstName);
            EditText LastName = (EditText)findViewById(R.id.lastName);
            EditText Email = (EditText)findViewById(R.id.email);
            EditText Contact = (EditText)findViewById(R.id.contact);
            EditText Address = (EditText)findViewById(R.id.address);

            FirstName.setText(editInfoList.get(0).getFirstName());
            LastName.setText(editInfoList.get(0).getLastName());
            Email.setText(editInfoList.get(0).getEmail());
            Contact.setText(editInfoList.get(0).getContactNo());
            Address.setText(editInfoList.get(0).getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyAccount(View view) throws IOException {

        EditText FirstName = (EditText) findViewById(R.id.firstName);
        EditText LastName = (EditText) findViewById(R.id.lastName);
        EditText Email = (EditText) findViewById(R.id.email);
        EditText Contact = (EditText) findViewById(R.id.contact);
        EditText Address = (EditText) findViewById(R.id.address);

        String fn = FirstName.getText().toString();
        String ln = LastName.getText().toString();
        String email = Email.getText().toString();
        String cn = Contact.getText().toString();
        String add = Address.getText().toString();

        ManageSession session = new ManageSession(this);
        String username = session.getUsername();

        /* Validation for unfilled fileds*/
        if (fn.trim().equals("")) {
            FirstName.setError("Please enter First Name...!");
        } else if (ln.trim().equals("")) {
            LastName.setError("Please enter Last Name...!");
        } else if (email.trim().equals("")) {
            Email.setError("Please enter Email Address...!");
        } else if (cn.trim().equals("")) {
            Contact.setError("Please enter Contact Number...!");
        } else if (add.trim().equals("")) {
            Address.setError("Please enter Postal Address...!");
        } else {


            User user = new User();
            String result = user.updateAccount(username, fn, ln, email, cn, add, this);


            if ("Email In".equals(result)) {
                Email.setError("Email already in use...!");
            }
            if ("Email format invalid".equals(result)) {
                Toast.makeText(this, R.string.err_invalid_email, Toast.LENGTH_SHORT).show();
            }
            if ("Contact format invalid".equals(result)) {
                Toast.makeText(this, R.string.err_invalid_contact, Toast.LENGTH_SHORT).show();
            }
            if ("Updated".equals(result)) {
                Toast.makeText(this, R.string.suc_up, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}

