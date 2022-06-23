package com.example.foodorderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.foodorderapp.Models.OrdersModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBNAME = "FoodApp.db";
    final static int VERSION = 4;

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE orders "+" (id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "name text,"+"phone text,"+"price int,"+"image int,"+"quantity int,"+"description text,"+"foodname text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(sqLiteDatabase);
    }

    public boolean insertorders(String name,String phone,int price,int image,String desc,String foodname,int quantity){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("description",desc);
        values.put("foodname",foodname);
        values.put("quantity",quantity);

        long id = database.insert("orders",null,values);

        if (id <= 0){
            return false;
        }else {
            return true;
        }
    }

    public ArrayList<OrdersModel> getOrders(){
        ArrayList<OrdersModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from orders",null);
        if (cursor.moveToFirst()){
            while ( cursor.moveToNext()){
                OrdersModel model = new OrdersModel();

                model.setOrderNumber(cursor.getInt(0)+"");
                model.setSoldItemName(cursor.getString(1));
                model.setPrice(cursor.getInt(3)+"");
                model.setOrderImage(cursor.getInt(4));
                orders.add(model);
                Log.d("raj123",model.getOrderNumber());
            }
        }
        cursor.close();
        database.close();
        return orders;
    }

    public Cursor getOrderById(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where id ="+ id,null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean updateorder(String name,String phone,int price,int image,String foodname,String desc,int quantity,int id){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("description",desc);
        values.put("foodname",foodname);
        values.put("quantity",quantity);
        Log.d("updated",id+"");

        long row = database.update("orders",values,"id="+id,null);

        if (row <= 0){
            return false;
        }else {
            return true;
        }
    }

    public int deleteorder(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("orders","id="+id,null);
    }
}
