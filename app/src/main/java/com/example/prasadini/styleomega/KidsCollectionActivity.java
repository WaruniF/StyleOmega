package com.example.prasadini.styleomega;

import android.os.Bundle;

import com.example.prasadini.styleomega.model.Navigation;

public class KidsCollectionActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kids_collection);
        createNavigation();
    }

}
