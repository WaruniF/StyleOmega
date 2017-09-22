package com.example.prasadini.styleomega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prasadini.styleomega.model.Item;
import com.example.prasadini.styleomega.model.ItemBean;
import com.example.prasadini.styleomega.model.ManageSession;
import com.example.prasadini.styleomega.model.Navigation;
import com.example.prasadini.styleomega.model.ShoppingCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends Navigation {

    ShoppingCart shoppingCart = new ShoppingCart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        createNavigation();

        ManageSession manageSession = new ManageSession(this);
        final String username = manageSession.getUsername();

        if (username.equals("")|| username.equals(null)){
            Toast.makeText(this, R.string.permission_rationale, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }

        LinearLayout vertical_main = (LinearLayout)findViewById(R.id.v_main);

        try {

            final List<ItemBean> itemBeanList = shoppingCart.displayShoppingCart(username, this);

            if (itemBeanList.isEmpty()){
                TextView emp = new TextView(this);
                emp.setText(R.string.err_noItems);
                vertical_main.addView(emp);
                /* Set Layout Params for Message */
                LinearLayout.LayoutParams layoutParamsMsg = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParamsMsg.setMargins(50, 100, 30, 30);
                emp.setLayoutParams(layoutParamsMsg);

            }else {
                for (final ItemBean itemBean : itemBeanList) {

                /* Horizontal sub_1 layout holding Image and the layout for Details*/
                    LinearLayout horizontal_sub1 = new LinearLayout(this);
                    horizontal_sub1.setOrientation(LinearLayout.HORIZONTAL);
                    vertical_main.addView(horizontal_sub1);

                /* Display Image */
                    final int imageID = this.getResources().getIdentifier(itemBean.getItemID().toLowerCase(), "drawable", this.getPackageName());
                    ImageView imageView = new ImageView(this);
                    imageView.setBackgroundResource(imageID);

                /* Setting Layout Params for Image and Details */
                    LinearLayout.LayoutParams lpImg = new LinearLayout.LayoutParams(300, 360);
                    lpImg.setMargins(30, 30, 30, 30);
                    lpImg.gravity = Gravity.CENTER;

                /* Vertical sub_sub_ layout holds Details of the Item */
                    LinearLayout vertical_sub_sub = new LinearLayout(this);
                    vertical_sub_sub.setOrientation(LinearLayout.VERTICAL);
                    horizontal_sub1.addView(vertical_sub_sub);

                /* Display details of the Product */
                    TextView textView = new TextView(this);
                    textView.setText(itemBean.getName() + "\n" + "Price: " + itemBean.getPrice() + "\n" + "Size: " + itemBean.getSize());

                /* Horizontal sub_2 layout holding Spinner and Buttons */
                    LinearLayout horizontal_sub2 = new LinearLayout(this);
                    horizontal_sub2.setOrientation(LinearLayout.HORIZONTAL);
                    vertical_main.addView(horizontal_sub2);

                /* Prompt Quantity text in the horizontal_sub2 */
                    TextView qtyTxt = new TextView(this);
                    qtyTxt.setText("Quantity: ");

                    final ArrayList arr = new ArrayList<>();
                    for (int i = 1; i <= Integer.parseInt(itemBean.getQty()); i++) {
                        arr.add(i);
                    }

                /* Spinner displaying available quantity */
                    final Spinner spinner = new Spinner(this);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
                    spinner.setAdapter(arrayAdapter);

                /* Update Button */
                    Button updateBtn = new Button(this);
                    updateBtn.setText(R.string.action_update);
                    updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                shoppingCart.updateShoppingCart(username, itemBean.getItemID(), itemBean.getSize(), Integer.parseInt(spinner.getSelectedItem().toString()),
                                        ShoppingCartActivity.this);
                                spinner.setSelection(arr.indexOf(Integer.parseInt(spinner.getSelectedItem().toString())));
                                Toast.makeText(getApplicationContext(), R.string.suc_update, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                /* Remove Button */
                    Button removeBtn = new Button(this);
                    removeBtn.setText(R.string.action_remove);
                    removeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                shoppingCart.removeFromCart(itemBean.getItemID(), username, itemBean.getSize(), ShoppingCartActivity.this);
                                itemBeanList.remove(itemBean);
                                Toast.makeText(getApplicationContext(), R.string.suc_remove, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                /* Set Layout Params for Spinner and Buttons*/
                    LinearLayout.LayoutParams layoutParamsBtns = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParamsBtns.setMargins(20, 20, 20, 20);

                /* Set Layout Params to the Attributes */
                    imageView.setLayoutParams(lpImg);
                    textView.setLayoutParams(lpImg);
                    qtyTxt.setLayoutParams(layoutParamsBtns);
                    updateBtn.setLayoutParams(layoutParamsBtns);
                    removeBtn.setLayoutParams(layoutParamsBtns);

                /* Add child views to the layout */
                    horizontal_sub1.addView(imageView);
                    horizontal_sub1.addView(textView);
                    horizontal_sub2.addView(qtyTxt);
                    horizontal_sub2.addView(spinner);
                    horizontal_sub2.addView(updateBtn);
                    horizontal_sub2.addView(removeBtn);
                }

            /* Horizontal sub_3 layout holding Checkout Button */
                LinearLayout horizontal_sub3 = new LinearLayout(this);
                horizontal_sub3.setOrientation(LinearLayout.HORIZONTAL);
                vertical_main.addView(horizontal_sub3);

            /* Checkout Button */
                Button checkoutBtn = new Button(this);
                checkoutBtn.setText(R.string.action_checkout);
                checkoutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ShoppingCartActivity.this, CheckoutActivity.class);
                        startActivity(intent);
                    }
                });

            /* Set Layout Params for Checkout Buttons*/
                LinearLayout.LayoutParams layoutParamsBtns = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParamsBtns.setMargins(30, 100, 30, 30);
                checkoutBtn.setLayoutParams(layoutParamsBtns);

            /* Add child views to the layout */
                horizontal_sub3.addView(checkoutBtn);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
