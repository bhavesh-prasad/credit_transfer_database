package com.example.sparks_foundation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class start_transaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_transaction);
    }

    public void send_credit(View view){
        EditText text1 = (EditText)findViewById(R.id.reciever_uid);
        EditText text2 = (EditText)findViewById(R.id.credit);
        int credit=0,reciever=0;
        try {
            reciever = Integer.parseInt(String.valueOf(text1.getText()));
        }catch(Exception e){
            Toast.makeText(this, "Please Enter A valid UID Number", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            credit = Integer.parseInt(String.valueOf(text2.getText()));
        }catch(Exception e){
            Toast.makeText(this, "Please Enter a Valid Credit Amount", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent= getIntent();
        int uid=intent.getIntExtra("uid",0);
        Log.d("Debug","Intent recieved with value uid="+uid);
        SQLiteDatabase db = openOrCreateDatabase("Database.db",MODE_PRIVATE,null);
        if(!check(uid,reciever,credit))
            return;
        db.execSQL("update users set credit_number =credit_number +" + credit + " where uid="+reciever);
        db.execSQL("update users set credit_number = credit_number-"+ credit + " where uid="+uid);
        Cursor cursor = db.rawQuery("Select * from users where uid="+uid,null);
        cursor.moveToFirst();
        String sender_name= cursor.getString(1)+" "+cursor.getString(2);
        cursor=db.rawQuery("Select * from users where uid="+reciever,null);
        cursor.moveToFirst();
        String reciever_name = cursor.getString(1)+" "+cursor.getString(2);
        db.execSQL("insert into logs(sender,reciever,credit_number) values (\""+sender_name+"\",\""+reciever_name+"\","+credit+")");
        Toast.makeText(this, "Credit Sent to Reciever", Toast.LENGTH_SHORT).show();
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public boolean check(int sender,int reciever,int credit){
        SQLiteDatabase db = openOrCreateDatabase("Database.db",MODE_PRIVATE,null);
        Cursor cursor ;
        if(sender==reciever)
        {
            Toast.makeText(this, "You cannot send money to yourself", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(sender<=0 || reciever <=0) {
            Toast.makeText(this, "Please Don't Enter a negative or zero value", Toast.LENGTH_SHORT).show();
            return false;
        }
        cursor=db.rawQuery("Select count(*) from users where uid="+reciever+"",null);
        cursor.moveToFirst();
        if(cursor.getInt(0)==0){
            Toast.makeText(this, "Reciever Id not found", Toast.LENGTH_SHORT).show();
            return false;
        }
        cursor=db.rawQuery("select credit_number from users where uid="+sender+"",null);
        cursor.moveToFirst();
        if(credit>cursor.getInt(0)){
            Toast.makeText(this, "You don't have that much amount of credit to send", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}