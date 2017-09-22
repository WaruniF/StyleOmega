package com.example.prasadini.styleomega.model;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prasadini.styleomega.AboutUsActivity;
import com.example.prasadini.styleomega.DisplayProductsActivity;
import com.example.prasadini.styleomega.HomeActivity;
import com.example.prasadini.styleomega.KidsCollectionActivity;
import com.example.prasadini.styleomega.ManageAccountActivity;
import com.example.prasadini.styleomega.MenCategoryActivity;
import com.example.prasadini.styleomega.R;
import com.example.prasadini.styleomega.SearchBarActivity;
import com.example.prasadini.styleomega.ShoppingCartActivity;
import com.example.prasadini.styleomega.SignInActivity;
import com.example.prasadini.styleomega.WomenCategoryActivity;
import com.example.prasadini.styleomega.model.Item;
import com.example.prasadini.styleomega.model.ItemBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasadini on 25/8/2017.
 */

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String GROUP = "com.prasadini.example.styleomega.GROUP";
    public static final String CATEGORY = "com.prasadini.example.styleomega.CATEGORY";
    public static final String EXTRA_NAME = "com.prasadini.example.styleomega.EXTRA_NAME";

    public void createNavigation(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search product...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchBarActivity.class);
                intent.putExtra("SEARCH",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_in) {
            ManageSession session = new ManageSession(this);
            String username = session.getUsername();

            if(username.equals("")|| username.equals(null)){
                startActivity(new Intent(this, SignInActivity.class));
            }
            else {
                startActivity(new Intent(this, HomeActivity.class));
            }
        } else if (id == R.id.home) {

        } else if (id == R.id.about_us) {
            startActivity(new Intent(this, AboutUsActivity.class));
        } else if (id == R.id.women) {
            startActivity(new Intent(this, WomenCategoryActivity.class));
        } else if (id == R.id.men) {
            startActivity(new Intent(this, MenCategoryActivity.class));
        } else if (id == R.id.kids) {
            startActivity(new Intent(this, KidsCollectionActivity.class));
        } else if (id == R.id.shopping_cart) {
            startActivity(new Intent(this, ShoppingCartActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoItems(View view){
        String tag = view.getTag().toString();
        String category = tag.split(",")[0];
        String group = tag.split(",")[1];

        Intent intent = new Intent(this, DisplayProductsActivity.class);
        intent.putExtra(CATEGORY, category);
        intent.putExtra(GROUP,group);
        startActivity(intent);
    }

    /*public void search(View view) throws IOException {
        EditText SearchTxt = (EditText) findViewById(R.id.searchTxt);
        String searchTxt = SearchTxt.getText().toString();

        if (searchTxt.trim().equals("")) {
            SearchTxt.setError("Please enter a key word to search...!");
        }
        else {
            Item item = new Item();
            ArrayList<ItemBean> array = item.displaySearchResults(searchTxt, this);

            if (array.isEmpty() || array.size() < 0) {
                Toast.makeText(getApplicationContext(), "Sorry, no results found...!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Navigation.this, SearchBarActivity.class);
                intent.putExtra(EXTRA_NAME, searchTxt);
                startActivity(intent);
            }
        }
    }*/

}

