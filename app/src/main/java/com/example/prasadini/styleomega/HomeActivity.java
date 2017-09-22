package com.example.prasadini.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.prasadini.styleomega.model.ManageSession;
import com.example.prasadini.styleomega.model.Navigation;

public class HomeActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        createNavigation();
    }

    public void gotoHome(View view){
        startActivity(new Intent(HomeActivity.this,MainActivity.class));
    }

    public void gotoShoppingCart(View view){
        startActivity(new Intent(HomeActivity.this,ShoppingCartActivity.class));
    }

    public void gotoEditProfile(View view){
        startActivity(new Intent(HomeActivity.this, ManageAccountActivity.class));
    }

    public void logout(View view){
        new ManageSession(this).logout();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

}
