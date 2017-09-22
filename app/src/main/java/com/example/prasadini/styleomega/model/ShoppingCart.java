package com.example.prasadini.styleomega.model;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasadini on 3/9/2017.
 */

public class ShoppingCart {
    /* Function adds items to the Shopping Cart */
    public String addToShoppingCart(String username, String itemID, String size, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * FROM shoppingcart WHERE Username = '"+username+"' AND itemID = '"+itemID+"' AND size = '"+size+"'";
        Cursor cursor1 = dbHelper.runSQL(query1);

        if (cursor1.moveToFirst()){
            do {
                return "In";
            }while (cursor1.moveToNext());
        }
        else {
            int qty = 1;
            String query2 = "INSERT INTO shoppingcart (`itemID`, `Username`, `quantity`, `size`) VALUES ('"+itemID+"', '"+username+"', '"+qty+"', '"+size+"')";
            dbHelper.executeSQL(query2);
            return "Added";
        }
    }

    public List<ItemBean> displayShoppingCart(String username, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * FROM shoppingcart WHERE Username = '"+username+"'";
        Cursor cursor1 = dbHelper.runSQL(query1);

        ArrayList<ItemBean> beanArrayList = new ArrayList<ItemBean>();

        if (cursor1.moveToFirst()){
            do {
                ItemBean itemBean = new ItemBean();
                itemBean.setItemID(cursor1.getString(0));
                String itemID = cursor1.getString(0);
                itemBean.setSize(cursor1.getString(3));
                // Function using another SELECT query
                String query2 = "SELECT * FROM item WHERE itemID = '"+itemID+"'";
                Cursor cursor2 = dbHelper.runSQL(query2);

                if (cursor2.moveToFirst()){
                    do {
                        itemBean.setName(cursor2.getString(1));
                        itemBean.setPrice(cursor2.getString(2));


                    }while (cursor2.moveToNext());
                }
                itemBean.setQty(cursor1.getString(2));
                beanArrayList.add(itemBean);
            }while (cursor1.moveToNext());
        }
        return beanArrayList;
    }

    public void updateShoppingCart(String username, String itemID, String size, int qty, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * FROM shoppingcart WHERE Username='"+username+"' AND itemID='"+itemID+"'  AND size='"+size+"' ";
        Cursor cursor1 = dbHelper.runSQL(query1);

        if (cursor1.moveToFirst()){
            do {
                String query2 = "UPDATE shoppingcart SET quantity='"+qty+"' WHERE Username='"+username+"' AND itemID='"+itemID+"'  AND size='"+size+"'";
                dbHelper.executeSQL(query2);
            }while (cursor1.moveToNext());
        }
    }

    public boolean removeFromCart(String itemID, String username, String size, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * FROM shoppingcart WHERE itemID = '"+itemID+"' AND Username = '"+username+"' AND size = '"+size+"'";
        Cursor cursor1 = dbHelper.runSQL(query1);

        if (cursor1.moveToFirst()){
            do {
                String query2 ="DELETE FROM shoppingcart WHERE itemID='"+itemID+"' AND Username='"+username+"' AND size='"+size+"'";
                dbHelper.executeSQL(query2);
                return true;
            }while (cursor1.moveToNext());
        }
        else {
            return false;
        }
    }

    public Cursor shoppingCartInfo(String username, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM shoppingcart WHERE Username = '"+username+"'";
        Cursor cursor = dbHelper.runSQL(query);
        return cursor;
    }

    public void deleteShoppingCartInfo(String username, Context context) throws IOException{
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * FROM shoppingcart WHERE Username='"+username+"'";
        Cursor cursor1 = dbHelper.runSQL(query1);

        if (cursor1.moveToFirst()){
            String query2 = "DELETE FROM shoppingcart WHERE Username='"+username+"' ";
            dbHelper.executeSQL(query2);
        }
    }

    public int displayCartQuantity(String itemID, String size, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM shoppingcart WHERE itemID = '"+itemID+"' AND size = '"+size+"'";
        Cursor cursor = dbHelper.runSQL(query);

        if (cursor.moveToFirst()){
            int quantity = Integer.parseInt(cursor.getString(2));
            return quantity;
        }
        else {
            return 0;
        }
    }

    public int calculateCartTotal(String itemID, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT quantity AND size FROM shoppingcart WHERE itemID = '"+itemID+"'"; // get the size and qty
        Cursor cursor1 = dbHelper.runSQL(query1);

        int total = 0;
        if (cursor1.moveToFirst()){
            do {
                int quantity = Integer.parseInt(cursor1.getString(2));
                String query2 = "SELECT price FROM item WHERE itemID = '"+itemID+"'"; // get the price
                Cursor cursor2 = dbHelper.runSQL(query2);
                if (cursor2.moveToFirst()){
                    do {
                        int price = Integer.parseInt(cursor2.getString(2));
                        total = price * quantity;
                    }while (cursor2.moveToNext());
                }

            }while (cursor1.moveToNext());
        }
        return total;

    }

}
