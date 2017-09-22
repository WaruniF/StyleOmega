package com.example.prasadini.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prasadini.styleomega.model.InquiryInfo;
import com.example.prasadini.styleomega.model.Item;
import com.example.prasadini.styleomega.model.ItemBean;
import com.example.prasadini.styleomega.model.ManageSession;
import com.example.prasadini.styleomega.model.Navigation;
import com.example.prasadini.styleomega.model.ReviewInfo;
import com.example.prasadini.styleomega.model.ShoppingCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DisplayAnItemActivity extends Navigation {

    public static final String ITEMID = "com.prasadini.example.styleomega.ITEMID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_an_item);
        createNavigation();

        ManageSession manageSession = new ManageSession(this);
        final String username = manageSession.getUsername();

        /* Retrieve the extended data from the intent */
        Intent intent = getIntent();
        final String itemID = intent.getStringExtra(DisplayProductsActivity.ITEMID);

        Item item = new Item();

        LinearLayout vertical_main = (LinearLayout)findViewById(R.id.v_main);

        try {

            List<ItemBean> itemArrList = item.displayAnItem(itemID, this);

            for(final ItemBean itemBean : itemArrList){

                /* New Vertical Layout holds Image, Details, Size Field and Buttons*/
                final LinearLayout vertical_sub1 = new LinearLayout(this);
                vertical_sub1.setOrientation(LinearLayout.VERTICAL);
                vertical_main.addView(vertical_sub1);

                /* Display Image */
                final int imageID = this.getResources().getIdentifier(itemBean.getItemID().toLowerCase(), "drawable", this.getPackageName());
                ImageView imageView = new ImageView(this);
                imageView.setBackgroundResource(imageID);

                /* Set Layout Params for Image */
                LinearLayout.LayoutParams layoutParamsImg = new LinearLayout.LayoutParams(800,860);
                layoutParamsImg.setMargins(20,120,20,20);
                imageView.setLayoutParams(layoutParamsImg);
                layoutParamsImg.gravity = Gravity.CENTER;

                /* Display details of the Product */
                TextView textView = new TextView(this);
                textView.setText("Name: " + itemBean.getName()+"\n"+ "Description: " + itemBean.getDescription() + "\n" +
                        "Price: Rs. " + itemBean.getPrice() +"\n"+ "Only " + itemBean.getQty() + " items left" );

                /* Text View prompt user for size */
                TextView sizeTxt = new TextView(this);
                sizeTxt.setText("Please select a Size: ");

                /* Set Layout Params for Item details and size prompt */
                LinearLayout.LayoutParams layoutParamsInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParamsInfo.setMargins(30,30,30,30);
                textView.setLayoutParams(layoutParamsInfo);
                sizeTxt.setLayoutParams(layoutParamsInfo);
                layoutParamsInfo.gravity = Gravity.CENTER;

                /* Retrieve available Sizes */
                ArrayList<ItemBean> sizeArr = item.displaySize(itemID,this);
                ArrayList<String> sizeList = new ArrayList<>();
                String availableSizes ="";
                for(ItemBean size :sizeArr){
                    sizeList.add(size.getSize());
                    availableSizes=availableSizes+size.getSize();
                }

                /* Spinner displaying available Sizes */
                final Spinner spinner = new Spinner(this);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sizeList);
                spinner.setAdapter(arrayAdapter);

                /* Add To Cart Button */
                Button addToCartBtn = new Button(this);
                addToCartBtn.setText(R.string.addBtn);
                vertical_sub1.setTag(itemBean.getItemID()+","+ itemBean.getSize());
                addToCartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tag = vertical_sub1.getTag().toString();
                        String itemID = tag.split(",")[0];

                        String size = spinner.getSelectedItem().toString();
                        gotoShoppingCart(itemID, size);

                    }
                });

                /* Inquire Button */
                Button inquireBtn = new Button(this);
                inquireBtn.setText(R.string.action_inquire);
                inquireBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DisplayAnItemActivity.this, InquireActivity.class);
                        intent.putExtra(ITEMID, itemID);
                        startActivity(intent);
                    }
                });

                /* Add Reviews Button */
                Button addReviewsBtn = new Button(this);
                addReviewsBtn.setText(R.string.addRBtn);
                addReviewsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DisplayAnItemActivity.this, AddReviewActivity.class);
                        intent.putExtra(ITEMID, itemID);
                        startActivity(intent);
                    }
                });

                /* Share Button */
                Button shareBtn = new Button(this);
                shareBtn.setText(R.string.shareBtn);
                shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Intent.ACTION_SEND);
                        intent1.setType("text/plain");
                        String msgBody = "Are you interested in " + itemBean.getName().toString() + " visit http://styleomega.com for more details";
                        intent1.putExtra(Intent.EXTRA_TEXT, msgBody);
                        startActivity(Intent.createChooser(intent1,"Share"));
                    }
                });

                /* Add child views to the layout */
                vertical_sub1.addView(imageView);
                vertical_sub1.addView(textView);
                vertical_sub1.addView(sizeTxt);
                vertical_sub1.addView(spinner);
                vertical_sub1.addView(addToCartBtn);
                vertical_sub1.addView(inquireBtn);
                vertical_sub1.addView(addReviewsBtn);
                vertical_sub1.addView(shareBtn);
            }

            /* New Vertical Layout holds Reviews and Ratings */
            final LinearLayout vertical_sub2 = new LinearLayout(this);
            vertical_sub2.setOrientation(LinearLayout.VERTICAL);
            vertical_main.addView(vertical_sub2);

            /* Set Layout Params for Reviews and Inquries */
            LinearLayout.LayoutParams lpInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lpInfo.setMargins(30,30,30,30);
            lpInfo.gravity = Gravity.CENTER;

            /* Text View for Reviews */
            TextView reviewsTxt = new TextView(this);
            reviewsTxt.setText("Reviews:");
            reviewsTxt.setLayoutParams(lpInfo);
            vertical_sub2.addView(reviewsTxt);

            /* Display Reviews */
            List<ReviewInfo> reviewArray = item.displayReviews(itemID,username,DisplayAnItemActivity.this);
            for(ReviewInfo reviews : reviewArray){

                TextView disReview = new TextView(this);
                disReview.setText("\t" + "Username: " +reviews.getUsername() +"\n"+ "\t"+reviews.getReview() +"\n");
                RatingBar ratingBar = new RatingBar(this);
                ratingBar.setRating(reviews.getRate());
                ratingBar.setLayoutParams(lpInfo);
                disReview.setLayoutParams(lpInfo);
                vertical_sub2.addView(disReview);
                vertical_sub2.addView(ratingBar);
            }

            /* New Vertical Layout holds Inquiries */
            LinearLayout vertical_sub3 = new LinearLayout(this);
            vertical_sub3.setOrientation(LinearLayout.VERTICAL);
            vertical_main.addView(vertical_sub3);

            /* Text View for Inquiries */
            TextView inqTxt = new TextView(this);
            inqTxt.setText("Inquiries:");
            inqTxt.setLayoutParams(lpInfo);
            vertical_sub2.addView(inqTxt);

            /* Display Inquiries */
            List<InquiryInfo> inquiryArray = item.displayInquiries(itemID,username,DisplayAnItemActivity.this);
            for(InquiryInfo inquiries : inquiryArray){

                TextView disInq = new TextView(this);
                disInq.setText("\t" + "Username: " +inquiries.getUsername() +"\n"+ "\t"+inquiries.getInquiry() +"\n");
                disInq.setLayoutParams(lpInfo);
                vertical_sub2.addView(disInq);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoShoppingCart(String itemID, String size){

        ManageSession manageSession = new ManageSession(this);
        String username = manageSession.getUsername();

        String result = null;

        ShoppingCart shoppingCart = new ShoppingCart();

        try {
            result = shoppingCart.addToShoppingCart(username, itemID, size, this);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (username.equals("")||username.equals(null)){
            Toast.makeText(this, R.string.permission_rationale, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignInActivity.class));
        }
        if (result.equals("In")){
                Toast.makeText(this, R.string.err_itemIn, Toast.LENGTH_SHORT).show();
        }
        if (result.equals("Added")){
                Toast.makeText(this, R.string.suc_addItem, Toast.LENGTH_SHORT).show();
        }

    }

}
