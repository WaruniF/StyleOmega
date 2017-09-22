package com.example.prasadini.styleomega;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prasadini.styleomega.model.DBHelper;
import com.example.prasadini.styleomega.model.Item;
import com.example.prasadini.styleomega.model.ItemBean;
import com.example.prasadini.styleomega.model.Navigation;

import java.io.IOException;
import java.util.List;

public class SearchBarActivity extends Navigation {
    public static final String ITEMID = "com.prasadini.example.styleomega.ITEMID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bar);
        createNavigation();

        /* Retrieve the extended data from the intent */
        Intent intent = getIntent();
        String searchQ = intent.getStringExtra("SEARCH");

        LinearLayout layout_main = (LinearLayout)findViewById(R.id.layout_main);

        try {

            List<ItemBean> searchArr = new Item().displaySearchResults(searchQ, this);

            if (searchArr.isEmpty()) {
                TextView emp = new TextView(this);
                emp.setText("No Items Found...");
                layout_main.addView(emp);
                /* Set Layout Params for Message */
                LinearLayout.LayoutParams layoutParamsMsg = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParamsMsg.setMargins(50, 100, 30, 30);
                emp.setLayoutParams(layoutParamsMsg);
            }else {

                for (ItemBean itemBean : searchArr) {

                /* Instantiate a new Child Layout */
                    LinearLayout layout_sub1 = new LinearLayout(this);
                    layout_sub1.setOrientation(LinearLayout.HORIZONTAL);
                    layout_main.addView(layout_sub1);

                /* Displays the Clickable Image */
                    final int imageID = this.getResources().getIdentifier(itemBean.getItemID().toLowerCase(), "drawable", this.getPackageName());
                    ImageView imageView = new ImageView(this);
                    layout_sub1.setTag(itemBean.getItemID());
                    layout_sub1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoAnItem(v);
                        }
                    });
                    imageView.setBackgroundResource(imageID);

                /* Set Layout Params */
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 560);
                    layoutParams.setMargins(20, 150, 20, 20);
                    imageView.setLayoutParams(layoutParams);

                /* Display the details in a Text View */
                    TextView txtDetails = new TextView(this);
                    txtDetails.setText(itemBean.getName() + "\n" + "Price:" + itemBean.getPrice());
                    txtDetails.setLayoutParams(layoutParams);

                /* Add Image and Details to the Layout */
                    layout_sub1.addView(imageView);
                    layout_sub1.addView(txtDetails);

                }
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
