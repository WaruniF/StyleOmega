package com.example.prasadini.styleomega.model;
import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Prasadini on 23/8/2017.
 */

public class User {

    public String validateLogin(String username, String password, Context context) throws IOException{
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM authentication WHERE Username = '"+username+"' AND Password = '"+password+"'";
        Cursor cursor = dbHelper.runSQL(query);

        if(cursor.moveToFirst()){
            do{
                return "Authenticated";
            }while (cursor.moveToNext());

        }else {
            return "Not Authenticated";
        }
    }

    public String registerUser (String username, String password, String re_pwd, String firstName, String lastName, String nic, String email, String contact, String address, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * FROM authentication WHERE Username = '"+username+"'";
        String query2 = "SELECT * FROM authentication WHERE Email = '"+email+"'";
        Cursor cursor2 = dbHelper.runSQL(query2);
        String result = null;

        Cursor cursor1 = dbHelper.runSQL(query1);

        if(username == null || password == null || re_pwd == null)

        if (cursor1.moveToFirst()){
                do{
                    result= "Username exist";
                }while (cursor1.moveToNext());
            }

           else if (!re_pwd.equals(password)){
                result= "Mis-matching passwords";
            }


            else if (cursor2.moveToFirst()){
                do{
                    result= "Email exist";
                }while (cursor2.moveToNext());
            }

            else if (!(email.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}"))){
                result= "Invalid email format";
            }

            else if (!contact.matches("\\d{3}-\\d{7}")){
                result= "Invalid contact number format";
            }
            else {
                String query3 = "INSERT INTO authentication (Username, Password, First_Name,Last_Name,NIC,Email,Re_Password,Contact,Address)" +
                        "VALUES('" + username + "','" + password + "','" + firstName + "','" + lastName + "','"
                        + nic + "','" + email + "','" + re_pwd + "', '" + contact + "', '"+address+"')";

                dbHelper.executeSQL(query3);
                result="Registered";
            }
        dbHelper.close();
        return result;
    }

    public String updateAccount(String username, String firstname, String lastname, String email, String contact, String address, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query1 = "SELECT * FROM authentication WHERE Email = '"+email+"' AND Username = '"+username+"'";
        Cursor cursor1 = dbHelper.runSQL(query1);

        if (cursor1.moveToFirst()){
            do {
                return "Email In";
            }while (cursor1.moveToNext());
        }
        else if (!(email.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}"))){
            return "Email format invalid";
        }
        else if (!contact.matches("\\d{3}-\\d{7}")){
            return "Contact format invalid";
        }
        else {
            String query2 = "UPDATE authentication SET First_Name = '"+firstname+"', Last_Name = '"+lastname+"', Email = '"+email+"', " +
                    "Contact = '"+contact+"', Address = '"+address+"' WHERE Username = '"+username+"'";
            dbHelper.executeSQL(query2);
            return "Updated";
        }
    }

    public ArrayList<UserBean> userDetails(String username, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT * FROM authentication WHERE Username = '"+username+"'";
        Cursor cursor = dbHelper.runSQL(query);
        ArrayList<UserBean> userInfo = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                UserBean userBean = new UserBean();
                userBean.setFirstName(cursor.getString(2));
                userBean.setLastName(cursor.getString(3));
                userBean.setEmail(cursor.getString(5));
                userBean.setContactNo(cursor.getString(7));
                userBean.setAddress(cursor.getString(8));
                userInfo.add(userBean);
            }while (cursor.moveToNext());
        }cursor.close();
        return userInfo;
    }

    public ArrayList<UserBean> displayDeliveryAddress(String username, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String query = "SELECT Address FROM authentication WHERE Username = '"+username+"'";
        Cursor cursor = dbHelper.runSQL(query);
        ArrayList<UserBean> deliveryAddList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                UserBean userBean = new UserBean();
                userBean.setAddress(cursor.getString(8));
                deliveryAddList.add(userBean);
            }while (cursor.moveToNext());
        }cursor.close();
        return deliveryAddList;
    }

    public void addReview(String username, String itemID, String review, float rate, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);

        String reviewID;
        String query1 = "SELECT substr(reviewID,2,4)+1 AS RID FROM review ORDER BY reviewID DESC LIMIT 1";
        Cursor cursor1 = dbHelper.runSQL(query1);

        if (cursor1.moveToFirst()){
            do {
                reviewID = "RI" + cursor1.getString(cursor1.getColumnIndex("RID"));
            }while (cursor1.moveToNext());
        }
        else {
            reviewID = "RI1000";
        }cursor1.close();
        String query2 = "INSERT INTO review (reviewID, itemID, Username, review, rate) VALUES ('"+reviewID+"', '"+itemID+"', '"+username+"', '"+review+"', '"+rate+"')";
        dbHelper.executeSQL(query2);
    }

    public void addInquiry(String username, String itemID, String inquiry, Context context) throws IOException {
        DBHelper dbHelper = new DBHelper(context);
        String inquiryID;
        String query1 = "SELECT substr(inquiryID,2,4)+1 AS IID FROM inquiry ORDER BY inquiryID DESC LIMIT 1";
        Cursor cursor1 = dbHelper.runSQL(query1);

        if (cursor1.moveToFirst()){
            do {
                inquiryID = "II" + cursor1.getString(cursor1.getColumnIndex("IID"));
            }while (cursor1.moveToNext());
        }
        else {
            inquiryID = "II1000";
        }cursor1.close();
        String query2 = "INSERT INTO inquiry (inquiryID, itemID, Username, inquiry, response) VALUES " +
                "('"+inquiryID+"', '"+itemID+"', '"+username+"', '"+inquiry+"', '"+null+"')";
        dbHelper.executeSQL(query2);
    }
}
