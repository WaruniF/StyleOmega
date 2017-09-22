package com.example.prasadini.styleomega.model;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasadini on 30/8/2017.
 */

public class Item {

    /* Function that displays all the product items */
    public List<ItemBean> displayAllItems(String category, String group, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM item WHERE category = '"+category+"' AND groups='"+group+"'";
        Cursor cursor = dbHelper.runSQL(query);
        ArrayList<ItemBean> arrayList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                ItemBean itemBean = new ItemBean();
                itemBean.setItemID(cursor.getString(0));
                itemBean.setName(cursor.getString(1));
                itemBean.setPrice(cursor.getString(2));
                itemBean.setQty(cursor.getString(3));
                itemBean.setDescription(cursor.getString(4));
                itemBean.setSize(cursor.getString(7));
                arrayList.add(itemBean);
            }while (cursor.moveToNext());
        }cursor.close();
        return arrayList;
    }

    /* Function that displays the detailed view of an item */
    public ArrayList<ItemBean> displayAnItem(String itemID, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM item WHERE itemID = '"+itemID+"'";
        Cursor cursor = dbHelper.runSQL(query);
        ArrayList<ItemBean> arrayList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                ItemBean itemBean = new ItemBean();
                itemBean.setItemID(cursor.getString(0));
                itemBean.setName(cursor.getString(1));
                itemBean.setPrice(cursor.getString(2));
                itemBean.setQty(cursor.getString(3));
                itemBean.setDescription(cursor.getString(4));
                itemBean.setSize(cursor.getString(7));
                arrayList.add(itemBean);
            }while (cursor.moveToNext());
        }cursor.close();
        return arrayList;
    }

    /* Function that displays available sizes */
    public ArrayList<ItemBean> displaySize(String itemID, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM size WHERE itemID = '"+itemID+"'";
        Cursor cursor = dbHelper.runSQL(query);

        ArrayList<ItemBean> sizes = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ItemBean sizeBean = new ItemBean();
                sizeBean.setSize(cursor.getString(1));
                if (Integer.parseInt(cursor.getString(2)) > 0){ // Checks qty > 0
                    sizes.add(sizeBean);
                }
            }while (cursor.moveToNext());
        }cursor.close();
        return sizes;
    }

    /* Function that displays total quantity */
    public ArrayList<ItemBean> displayQty(String itemID, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM item WHERE itemID = '"+itemID+"'";
        Cursor cursor = dbHelper.runSQL(query);

        ArrayList<ItemBean> qty = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ItemBean qtyBean = new ItemBean();
                qtyBean.setQty(cursor.getString(3));
                /*if (Integer.parseInt(cursor.getString(3)) < 1){ // Checks qty > 0
                    qty.add(qtyBean);
                }*/
            }while (cursor.moveToNext());
        }cursor.close();
        return qty;
    }

    /* Function that displays available quantity */
    public int displayQuantity(String itemID, String size, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM item WHERE itemID = '"+itemID+"' AND size = '"+size+"'";
        Cursor cursor = dbHelper.runSQL(query);

        if (cursor.moveToFirst()){
            int quantity = Integer.parseInt(cursor.getString(3));
            return quantity;
        }
        else {
            return 0;
        }
    }

    /* Function that displays the total quantity of each item ID */
    public int displayTotalQty(String itemID, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT quantity FROM size WHERE itemID = '"+itemID+"'";
        Cursor cursor = dbHelper.runSQL(query);
        int total = 0;
        if (cursor.moveToFirst()){
            do {
                int quantity = Integer.parseInt(cursor.getString(2));
                total = quantity + total;
            }while (cursor.moveToNext());
        }
        return total;
    }

    public ArrayList<ReviewInfo> itemReviews(String username, Context context) throws IOException {
        ArrayList<ReviewInfo> arrayList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM review WHERE Username = '"+username+"'";
        Cursor cursor = dbHelper.runSQL(query);

        if (cursor.moveToFirst()){
            do {
                ReviewInfo reviewInfo = new ReviewInfo();
                reviewInfo.setUsername(cursor.getString(2));
                reviewInfo.setReview(cursor.getString(3));
                reviewInfo.setRate(Integer.parseInt(cursor.getString(4)));
                arrayList.add(reviewInfo);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    public void updateStock(String username, Context context) throws IOException{
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * from shoppingcart WHERE Username='"+username+"'";
        Cursor cursor1 = dbHelper.runSQL(query1);
        if(cursor1.moveToFirst()){
            do {
                String itemID = cursor1.getString(0);
                String size  = cursor1.getString(3);
                String qty = cursor1.getString(2);
                String query2 = "UPDATE size SET quantity = quantity - '"+qty+"' WHERE itemID='"+itemID+"' AND size='"+size+"'";
                dbHelper.executeSQL(query2);

                String query3 = "UPDATE item SET qty =qty-'"+qty+"' WHERE itemID = '"+itemID+"'";
                dbHelper.executeSQL(query3);
            }while (cursor1.moveToNext());
        }cursor1.close();
    }

    public ArrayList displaySearchResults(String word, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM item WHERE (' '||name||' ') LIKE '% "+word+" %' OR " +
                "(' '||price||' ') LIKE '% "+word+" %' OR (' '||description||' ') LIKE '% "+word+" %' OR" +
                " (' '||groups||' ') LIKE '% "+word+" %' OR (' '||category||' ') LIKE '% "+word+" %'";
        Cursor cursor = dbHelper.runSQL(query);

        ArrayList<ItemBean> searchResults = new ArrayList<ItemBean>();

        if (cursor.moveToFirst()){
            do {
                ItemBean itemBean = new ItemBean();
                itemBean.setItemID(cursor.getString(0));
                itemBean.setName(cursor.getString(1));
                itemBean.setPrice(cursor.getString(2));
                searchResults.add(itemBean);
            }while (cursor.moveToNext());
        }cursor.close();
        return searchResults;
    }

    public ArrayList displayReviews(String itemID, String username, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM review WHERE itemID='"+itemID+"'";
        Cursor cursor = dbHelper.runSQL(query);

        ArrayList<ReviewInfo> arr = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                ReviewInfo reviewInfo = new ReviewInfo();
                reviewInfo.setUsername(cursor.getString(2));
                reviewInfo.setReview(cursor.getString(3));
                reviewInfo.setRate(cursor.getFloat(4));
                arr.add(reviewInfo);
            }while (cursor.moveToNext());
        }cursor.close();
        return arr;
    }

    public ArrayList displayInquiries(String itemID, String username, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM inquiry WHERE itemID='"+itemID+"'";
        Cursor cursor = dbHelper.runSQL(query);

        ArrayList<InquiryInfo> arr = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                InquiryInfo inquiryInfo = new InquiryInfo();
                inquiryInfo.setUsername(cursor.getString(2));
                inquiryInfo.setInquiry(cursor.getString(3));
                arr.add(inquiryInfo);
            }while (cursor.moveToNext());
        }cursor.close();
        return arr;
    }
}
