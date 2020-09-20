package com.example.sparks_foundation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = openOrCreateDatabase("Database.db",MODE_PRIVATE,null);
            db.execSQL("Create table if not exists USERS ( uid integer primary key AUTOINCREMENT, fname text , lname text , credit_number number );");
            db.execSQL("Create table if not exists LOGS (  TRANSACTION_ID integer primary key AUTOINCREMENT, Sender text  , Reciever text ,credit_number number);");
            Cursor cursor = db.rawQuery("Select count (*) from users",null);
            cursor.moveToFirst();
            if(cursor.getInt(0)==0) {
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Bhavesh\" , \"Prasad\", 1000 );");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Aditya\" , \"Akash\", 500);");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Alex\" , \"Roy\", 5200 );");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Frank\" , \"Borne\", 1500);");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Summer\" , \"Stay\", 5300 );");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"John\" , \"Doe\", 7500 );");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Alex\" , \"Teidmann\", 4500 );");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Regina\" , \"Filangae\",2500 );");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Joey\" , \"Tribi\", 3500 );");
                db.execSQL("Insert into users(fname,lname,credit_number) values(\"Ash\" , \"Bourne\", 2500 );");
                db.execSQL("Insert into logs(TRANSACTION_ID,Sender,Reciever,credit_number) values(1000,\"Ash Bourne\" , \"Bhavesh Prasad\", 500 );");
            }
        showusers();
        db.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void showusers()
    {
        SQLiteDatabase db = openOrCreateDatabase("Database.db",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("Select * from USERS",null);
        cursor.moveToFirst();
        do{
            LinearLayout view = (LinearLayout) findViewById(R.id.user_view);
            Button btn  = new Button(MainActivity.this);
            btn.setText(cursor.getString(0)+"  "+cursor.getString(1)+" "+cursor.getString(2)+
                    " Credit: "+cursor.getString(3));
            btn.setOnClickListener(this);
            btn.setId(cursor.getInt(0));
            view.addView(btn);
        }while(cursor.moveToNext());
        db.close();

    }
    public void view_transactions(View view){
        Intent intent = new Intent(this,view_transactions.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        Log.d("debug","Here");
        Intent intent = new Intent(this,start_transaction.class);
        intent.putExtra("uid",((Button)v).getId());
        Log.d("Debug",String.valueOf(((Button)v).getId())+" Selected");
        startActivity(intent);

    }
}