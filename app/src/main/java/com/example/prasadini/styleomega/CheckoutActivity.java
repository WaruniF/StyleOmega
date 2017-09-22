package com.example.prasadini.styleomega;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prasadini.styleomega.model.Item;
import com.example.prasadini.styleomega.model.ItemBean;
import com.example.prasadini.styleomega.model.ManageSession;
import com.example.prasadini.styleomega.model.Navigation;
import com.example.prasadini.styleomega.model.ShoppingCart;
import com.example.prasadini.styleomega.model.User;

import java.io.IOException;
import java.util.List;

public class CheckoutActivity extends Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        createNavigation();

        ManageSession manageSession = new ManageSession(this);
        final String username = manageSession.getUsername();

        final ShoppingCart shoppingCart = new ShoppingCart();
        final Item item = new Item();

        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.main);

        try {

            int subtotal = 0;
            List<ItemBean> array = shoppingCart.displayShoppingCart(username,CheckoutActivity.this);

            for(final ItemBean itemBean : array){

                /* Horizontal Sub layout holding Image and Product Details*/
                LinearLayout h_subLayout = new LinearLayout(this);
                h_subLayout.setOrientation(LinearLayout.HORIZONTAL);
                mainLayout.addView(h_subLayout);

                /* Display Image */
                final int imageID = this.getResources().getIdentifier(itemBean.getItemID().toLowerCase(), "drawable", this.getPackageName());
                ImageView imageView = new ImageView(this);
                imageView.setBackgroundResource(imageID);

                /* Setting Layout Params for Image and Details */
                LinearLayout.LayoutParams lpImg = new LinearLayout.LayoutParams(300,360);
                lpImg.setMargins(30,30,30,30);
                lpImg.gravity = Gravity.CENTER;

                /* Display details of the Product */
                TextView textView = new TextView(this);
                textView.setText(itemBean.getName()+"\n"+"Size: " +itemBean.getSize()+ "\n" + "LKR: "+itemBean.getPrice() +
                        " X " + shoppingCart.displayCartQuantity(itemBean.getItemID(),itemBean.getSize(), this));

                /* Set Layout Params to the Attributes */
                imageView.setLayoutParams(lpImg);
                textView.setLayoutParams(lpImg);

                /* Add child views to the layout */
                h_subLayout.addView(imageView);
                h_subLayout.addView(textView);

                subtotal = subtotal + (Integer.parseInt(itemBean.getQty()) * Integer.parseInt(itemBean.getPrice()));
             }

             /* Setting Layout Params for Sub Total, Credit Card Details and Purchase Button */
            LinearLayout.LayoutParams lpOther = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpOther.setMargins(30,30,30,30);

            /* Display Sub Total */
            TextView totalTxt = new TextView(this);
            totalTxt.setText("Sub Total: LKR " + subtotal);

            /* Prompt User for Credit Card Details */
            EditText editTextCC = new EditText(this);
            editTextCC.setHint(R.string.ccInput);
            editTextCC.setMaxLines(1);

            /* CC Details validation */
            String ccDetails = editTextCC.getText().toString();
            if (ccDetails.trim().equals("")) {
                editTextCC.setError("Please do not leave field unfilled...!");
            }

            /* PURCHASE Button*/
            Button purchaseBtn = new Button(this);
            purchaseBtn.setText(R.string.action_purchase);
            purchaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        item.updateStock(username,CheckoutActivity.this);
                        shoppingCart.deleteShoppingCartInfo(username,CheckoutActivity.this);
                        Toast.makeText(getApplicationContext(), "Successfully purchased items...!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            /* Set Layout Params to the Attributes */
            totalTxt.setLayoutParams(lpOther);
            editTextCC.setLayoutParams(lpOther);
            purchaseBtn.setLayoutParams(lpOther);

            /* Add child views to the layout */
            mainLayout.addView(totalTxt);
            mainLayout.addView(editTextCC);
            mainLayout.addView(purchaseBtn);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
