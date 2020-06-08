package com.app.dlmobiletp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ajouter_absence extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_absence);
        fillspinner();
        final Spinner spinner = (Spinner)findViewById(R.id.spinner_ab);
        Button btn_ab = (Button)findViewById(R.id.Btn_ajouter_ab);
        btn_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(v.getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("ids", nametoid(spinner.getSelectedItem().toString()));
                values.put("date", getDateTime());

                long id = db.insert("abs", null, values);



                Toast.makeText(ajouter_absence.this, "done !", Toast.LENGTH_SHORT).show();
                db.close();
                dbHelper.close();
            }
        });

    }
    public void fillspinner(){
        List<String> stagiereArray =  new ArrayList<String>();
        Spinner spinner = (Spinner)findViewById(R.id.spinner_ab);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Stagiaire", null);
        while(cursor.moveToNext()){
            stagiereArray.add(cursor.getString(1)+" "+cursor.getString(2));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stagiereArray);
        spinner.setAdapter(adapter);
        cursor.close();
        db.close();
        dbHelper.close();
    }
    public int nametoid(String nom){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String  sqlst="select code from Stagiaire where nom||' '||prenom = '"+nom+"'";
        Cursor cursor = db.rawQuery(sqlst, null);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return -1;
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
