package com.example.sparks_foundation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class view_transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);
        setView();
    }
    public void setView(){
        SQLiteDatabase db = openOrCreateDatabase("Database.db",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("Select * from LOGS",null);
        cursor.moveToFirst();
        do{
            LinearLayout view = (LinearLayout) findViewById(R.id.transactions_view);
            TextView table  = new TextView(view_transactions.this);
            table.setPadding(30,20,30,20);
            table.setText("Transaction id:"+cursor.getString(0)+" happened from user: "+cursor.getString(1)
            +" to user: " + cursor.getString(2)+" for amount : "+ cursor.getString(3)+"");
            view.addView(table);
        }while(cursor.moveToNext());
        db.close();
    }
}