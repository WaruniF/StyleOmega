package com.example.prasadini.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.prasadini.styleomega.model.ItemBean;
import com.example.prasadini.styleomega.model.ManageSession;
import com.example.prasadini.styleomega.model.Navigation;
import com.example.prasadini.styleomega.model.ReviewInfo;
import com.example.prasadini.styleomega.model.User;

import java.io.IOException;

public class AddReviewActivity extends Navigation {

    public static final String ITEMID = "com.prasadini.example.styleomega.ITEMID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);
        createNavigation();
    }

    public void uploadReview(View view){

        ManageSession manageSession = new ManageSession(this);
        String username = manageSession.getUsername();

        /* Retrieve the extended data from the intent */
        Intent intent = getIntent();
        String itemID = intent.getStringExtra(DisplayAnItemActivity.ITEMID);

        /* Review Text Validation */
        EditText ReviewText = (EditText) findViewById(R.id.reviewText);
        String reviewText = ReviewText.getText().toString();
        if (reviewText.trim().equals("")) {
            ReviewText.setError("Cannot upload an empty review...!");
        }

        /* Retrieve Rate */
        RatingBar Rate = (RatingBar) findViewById(R.id.ratingBar);
        Float rate = Rate.getRating();

        if (username == null || username.equals("")){
            Toast.makeText(this, R.string.permission_rationale, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignInActivity.class));
        }
        else{
            try {
                new User().addReview(username, itemID, reviewText, rate,this);
                Toast.makeText(getApplicationContext(), "Successfully uploaded your inquiry...!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(AddReviewActivity.this, DisplayAnItemActivity.class);
                intent1.putExtra(ITEMID, itemID);
                startActivity(intent1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


