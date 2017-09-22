package com.example.prasadini.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prasadini.styleomega.model.Item;
import com.example.prasadini.styleomega.model.ItemBean;
import com.example.prasadini.styleomega.model.Navigation;

import java.io.IOException;
import java.util.List;

public class DisplayProductsActivity extends Navigation {
    public static final String ITEMID = "com.prasadini.example.styleomega.ITEMID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_products);
        createNavigation();

        /* Retrieve the extended data from the intent */
        Intent intent = getIntent();
        String category = intent.getStringExtra(Navigation.CATEGORY);
        String group = intent.getStringExtra(Navigation.GROUP);

        LinearLayout parentLinearLayout = (LinearLayout)findViewById(R.id.linear_displayAll);

        try {

            List<ItemBean> itemArrList = new Item().displayAllItems(category, group, this);

            for(final ItemBean itemBean : itemArrList){

                /* Instantiate a new Child Layout */
                LinearLayout childLinearLayout = new LinearLayout(this);
                childLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                parentLinearLayout.addView(childLinearLayout);

                /* Displays the Clickable Image */
                final int imageID = this.getResources().getIdentifier(itemBean.getItemID().toLowerCase(), "drawable", this.getPackageName());
                ImageView imageView = new ImageView(this);
                childLinearLayout.setTag(itemBean.getItemID());
                childLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoAnItem(v);
                    }
                });
                imageView.setBackgroundResource(imageID);

                /* Set Layout Params */
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500,560);
                layoutParams.setMargins(20,150,20,20);
                imageView.setLayoutParams(layoutParams);

                /* Display the details in a Text View */
                TextView txtDetails = new TextView(this);
                txtDetails.setText(itemBean.getName()+"\n"+"Price:" + itemBean.getPrice());
                txtDetails.setLayoutParams(layoutParams);

                /* Add Image and Details to the Layout */
                childLinearLayout.addView(imageView);
                childLinearLayout.addView(txtDetails);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void gotoAnItem(View view){
        String itemID = view.getTag().toString();
        Intent intent = new Intent(this, DisplayAnItemActivity.class);
        intent.putExtra(ITEMID, itemID);
        startActivity(intent);
    }

}
